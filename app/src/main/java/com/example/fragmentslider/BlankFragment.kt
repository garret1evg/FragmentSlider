package com.example.fragmentslider

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
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
            val inAppNotifyText: AppCompatTextView = view.findViewById(R.id.in_app_notify_text)
            counter = getInt(ARG_COUNTER)
            counterTextView.text = counter.toString()
            inAppNotifyText.text = context?.resources?.getString(
                R.string.notification_text,
                counter
            )

        }
        setClicks(view)
    }
    private fun setClicks(view: View){
        val plus = view.findViewById<FloatingActionButton>(R.id.fab_plus)
        val minus = view.findViewById<FloatingActionButton>(R.id.fab_minus)
        val createNotify = view.findViewById<AppCompatButton>(R.id.create_notify)

        if (counter == 1)
            minus.visibility = View.GONE
        plus.setOnClickListener{
            (activity as MainActivity).addNewFragment()
        }
        minus.setOnClickListener{
            (activity as MainActivity).deleteFragment(counter)
        }
        createNotify.setOnClickListener{
            (activity as MainActivity).createNotifications(counter)
            showInAppNotify(view)

        }
    }

    private fun showInAppNotify(view: View) {
        val inAppNotify = view.findViewById<LinearLayout>(R.id.in_app_notify)
        val animFadeIn: Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_in
        )
        val animFadeOut: Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_out
        )
        inAppNotify.startAnimation(animFadeIn)
        inAppNotify.visibility = View.VISIBLE
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(Runnable {
            inAppNotify.startAnimation(animFadeOut)
        }, 1000)
        handler.postDelayed(Runnable {
            inAppNotify.visibility = View.GONE
        }, 1500)
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