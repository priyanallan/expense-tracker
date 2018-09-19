package com.example.priya.expensa

import android.animation.ArgbEvaluator
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import kotlinx.android.synthetic.main.activity_on_boarding.*
import kotlinx.android.synthetic.main.fragment_on_boarding.*
import kotlinx.android.synthetic.main.fragment_on_boarding.view.*

class OnBoardingActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var page = 0
    private val TAG = "OnBoardingActivity"
    private lateinit var indicators : Array<ImageView>
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val color1 = ContextCompat.getColor(this, R.color.blue_grey)
        val color2 = ContextCompat.getColor(this, R.color.red)
        val color3 = ContextCompat.getColor(this, R.color.pink)

        val colorList = arrayOf(color1, color2, color3)

        indicators = arrayOf(carousel_indicator_0, carousel_indicator_1, carousel_indicator_2)

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        introViewPager.adapter = mSectionsPagerAdapter
        introViewPager.currentItem = page
        updateIndicators(page)

        val evaluator = ArgbEvaluator()

        introViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                var colorUpdate: Int = evaluator.evaluate(positionOffset, colorList[position], colorList[if (position == 2) position else position + 1]) as Int
                introViewPager.setBackgroundColor(colorUpdate)
            }

            override fun onPageSelected(position: Int) {
                page = position
                updateIndicators(page)

                when (position) {
                    0 -> introViewPager.setBackgroundColor(color1)
                    1 -> introViewPager.setBackgroundColor(color2)
                    2 -> introViewPager.setBackgroundColor(color3)
                }
                next_intro_button.visibility = if (position == 2) View.GONE else View.VISIBLE
                finish_intro_button.visibility = if (position == 2) View.VISIBLE else View.GONE
            }

        })

        skip_intro_button.setOnClickListener { finish() }
        next_intro_button.setOnClickListener {
            page += 1
            introViewPager.setCurrentItem(page, true)
        }
        finish_intro_button.setOnClickListener {
            finish()
            SharedPref.getInstance(this).setIsFirstLaunchToFalse()
        }
    }

    private fun updateIndicators(position : Int) {
     for (i: Int in 0..(indicators.size-1)){
        indicators[i].setBackgroundResource(if(i == position) R.drawable.indicator_selected else R.drawable.indicator_unselected)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_on_boarding, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when(position){
                0 -> return "PAGE 1"
                1 -> return "PAGE 2"
                2 -> return "PAGE 3"
            }
            return null
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        val bgs = arrayOf(R.drawable.ic_baseline_money_24px, R.drawable.ic_baseline_money_24px, R.drawable.ic_baseline_money_24px)

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_on_boarding, container, false)
            rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))

//            val imageSize = arguments?.getInt(ARG_SECTION_NUMBER) - 1
//            val imageSize = 3
//            intro_image.setBackgroundResource(bgs[imageSize-1])
            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
