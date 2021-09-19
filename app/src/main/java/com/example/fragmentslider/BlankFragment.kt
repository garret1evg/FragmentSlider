package com.example.fragmentslider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val ARG_COUNTER = "counter"

class BlankFragment : Fragment() {
    var counter: Int = 0
    private set

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_COUNTER) }?.apply {
            val counterTextView: AppCompatTextView = view.findViewById(R.id.counter)
            counter = getInt(ARG_COUNTER)
            counterTextView.text = counter.toString()
        }
        setClicks(view)
    }
    private fun setClicks(view: View){
        val plus = view.findViewById<FloatingActionButton>(R.id.fab_plus)
        val minus = view.findViewById<FloatingActionButton>(R.id.fab_minus)
        if (counter == 1)
            minus.visibility = View.GONE
        plus.setOnClickListener{
            (activity as MainActivity).addNewFragment()
        }
        minus.setOnClickListener{
            (activity as MainActivity).deleteFragment(counter)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(counter: Int) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COUNTER, counter)
                }
            }
    }
}