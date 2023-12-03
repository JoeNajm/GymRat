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
import com.androidplot.xy.CatmullRomInterpolator
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.PanZoom
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYGraphWidget
import com.androidplot.xy.XYSeries
import com.example.finalgymlog.data.SessionViewModel
import com.example.finalgymlog.databinding.FragmentStatisticsBinding
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.util.Arrays


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

        binding.buttonBackArrow.setOnClickListener {
            findNavController().navigate(R.id.action_statisticsFragment_to_sessionListFragment)
        }

        val session = viewModel.readAllSession.value
        val MODE = "weight"

        var dates = arrayOf<String>()
        var data = arrayOf<Double>()

        if(MODE == "weight"){
            for (i in session!!.reversed()) {
                if (i.body_weight!!.toDouble() != 0.0) {
                    data += i.body_weight.toDouble()
                    dates += i.date.takeLast(5)
                }
            }
        }



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

        PanZoom.attach(binding.plot)


        return root
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

}