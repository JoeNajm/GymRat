package com.example.finalgymlog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.androidplot.xy.BoundaryMode
import com.androidplot.xy.CatmullRomInterpolator
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.PanZoom
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYGraphWidget
import com.androidplot.xy.XYSeries
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.data.SessionViewModel
import com.example.finalgymlog.databinding.FragmentStatisticsBinding
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.util.Arrays


@Suppress("DEPRECATION")
class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SessionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var STATE = "ten_days"

        binding.buttonBackArrow.setOnClickListener {
            findNavController().navigate(R.id.action_statisticsFragment_to_sessionListFragment)
        }

        val session = viewModel.readAllSession.value

        if (session != null) {
            if(session.size == 0){
                binding.buttonTenDays.visibility = View.GONE
                binding.buttonAllDays.visibility = View.GONE
                binding.plot.visibility = View.GONE
                binding.textMode.visibility = View.GONE
                binding.textNoSession.visibility = View.VISIBLE
            }
            else{
                binding.buttonTenDays.visibility = View.VISIBLE
                binding.buttonAllDays.visibility = View.VISIBLE
                binding.plot.visibility = View.VISIBLE
                binding.textMode.visibility = View.VISIBLE
                binding.textNoSession.visibility = View.GONE
                plot_graph(STATE, session)
            }
        }
        binding.buttonAllDays.setOnClickListener {
            binding.textMode.setText("All days")
            binding.buttonAllDays.backgroundTintList = resources.getColorStateList(R.color.green)
            binding.buttonTenDays.backgroundTintList = resources.getColorStateList(R.color.red)
            STATE = "all_days"
            plot_graph(STATE, session)
        }
        binding.buttonTenDays.setOnClickListener {
            binding.textMode.setText("Last 10 days")
            binding.buttonAllDays.backgroundTintList = resources.getColorStateList(R.color.red)
            binding.buttonTenDays.backgroundTintList = resources.getColorStateList(R.color.green)
            STATE = "ten_days"
            plot_graph(STATE, session)
        }


        return root
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    fun plot_graph(STATE: String, session: List<Session>?){

        binding.plot.clear()

        var dates = arrayOf<String>()
        var data = arrayOf<Double>()

        println("All Sessions: ")
        println(session!!.reversed())

        if(STATE == "ten_days"){
            var j_idx = 0
            for (i in session) {
                if (i.body_weight!!.toDouble() != 0.0) {
                    if (j_idx < 10){
                        data += i.body_weight.toDouble()
                        dates += i.date.takeLast(5)
                        j_idx += 1
                    }
                }
            }
            data = data.reversed().toTypedArray()
            dates = dates.reversed().toTypedArray()
            println("Last 10 days :")
            println(data)
        }
        else if(STATE == "all_days"){
            for (i in session!!.reversed()) {
                if (i.body_weight!!.toDouble() != 0.0) {
                    data += i.body_weight.toDouble()
                    dates += i.date.takeLast(5)
                }
            }
        }

        println(dates.contentToString())
        println(data.contentToString())

        val series1 : XYSeries = SimpleXYSeries(
            Arrays.asList(* data),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY
            ,"Body weight")

        val series1Format = LineAndPointFormatter(Color.BLUE,Color.BLACK,null,null)

        series1Format.setInterpolationParams(
            CatmullRomInterpolator.Params(10,
                CatmullRomInterpolator.Type.Centripetal))


        binding.plot.addSeries(series1,series1Format)

        binding.plot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = object : Format() {
            override fun format(
                obj: Any?,
                toAppendTo: StringBuffer,
                pos: FieldPosition
            ): StringBuffer {
                val i = Math.round((obj as Number).toFloat())
                return toAppendTo.append(dates[i])
            }
            override fun parseObject(source: String?, pos: ParsePosition): Any? {
                return null
            }
        }

        val isEqual = data.distinct().count() == 1
        println(isEqual)
        if(isEqual){
            binding.plot.setRangeBoundaries(data[0]-2, data[0]+2, BoundaryMode.FIXED)
        }
        else{
            binding.plot.setRangeBoundaries(data.min(), data.max(), BoundaryMode.FIXED)
        }

        PanZoom.attach(binding.plot)
        binding.plot.redraw()
    }

//    fun <T> allEqual(vararg entries: T): Boolean = entries.all { it == entries[0] }


}