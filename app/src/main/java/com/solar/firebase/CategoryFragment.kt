package com.solar.firebase

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView



class CategoryFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val subway = categorySelectData?.subway?.map(::convertSubway)?: listOf()
        val locale = categorySelectData?.locale?.map(::convertLocale)?: listOf()
        showLocaleCategory(locale)*/
    }

    private fun setTabTypeFace(isSubway: Boolean) {
        /*val localeLayout = (bind.categorySelectTabGroup.getChildAt(0) as ViewGroup).getChildAt(0) as LinearLayout
        val subwayLayout = (bind.categorySelectTabGroup.getChildAt(0) as ViewGroup).getChildAt(1) as LinearLayout

        val localeText = localeLayout.getChildAt(1) as TextView
        val subwayText = subwayLayout.getChildAt(1) as TextView

        if (isSubway) {
            localeText.typeface = Typeface.DEFAULT
            subwayText.typeface = Typeface.DEFAULT_BOLD
        } else {
            localeText.typeface = Typeface.DEFAULT_BOLD
            subwayText.typeface = Typeface.DEFAULT
        }*/
    }

    /*private fun showLocaleCategory(list: List<CategoryHead>) {
        if (list.isNotEmpty()) {
            setTabTypeFace(false)
            bind.categorySelectLocaleListView.visibility = View.GONE
            bind.categorySelectHeadListView.adapter = CategoryHeadAdapter { bodies ->
                initCategoryBodyAdapter(bind.categorySelectBodyListView, bodies)
            }.apply {
                list[0].isFocus = true
                submitList(list)
                initCategoryBodyAdapter(bind.categorySelectBodyListView, list[0].bodies)
            }
        }
    }

    private var onClickCategory = { id: Int, title: String ->

    }

    private fun initCategoryBodyAdapter(recyclerView: RecyclerView, bodies: List<CategoryBody>) {
        recyclerView.adapter = CategoryBodyAdapter(onClickCategory).apply {
            submitList(bodies)
        }
    }

    private fun convertSubway(subway: PopShopSubwayItemResponse): CategoryHead = CategoryHead(
            subway.id,
            subway.name,
            subway.stations.map(::convertStationBody),
            isFocus = false,
            isActive = true,
            isSubway = true,
            subwayColor = subway.color_code
    )

    private fun convertLocale(locale: PopShopLocaleItemResponse): CategoryHead = CategoryHead(
            0,
            locale.name,
            locale.hot_spots.map(::convertLocaleBody),
            isFocus = false,
            isActive = true,
            isSubway = false,
            subwayColor = ""
    )

    private fun convertStationBody(station: PopShopSubwayStationResponse): CategoryBody = CategoryBody (
            station.id?:0,
            station.name?:"",
            station.has_pop?:false
    )

    private fun convertLocaleBody(localeBody: PopShopLocaleHotSpotResponse) = CategoryBody(
            localeBody.id,
            localeBody.name_k,
            localeBody.has_pop
    )*/

    companion object {
        fun newInstance(): CategoryFragment =
                CategoryFragment().apply {
                    /*arguments = Bundle().apply {
                        putParcelable("pop_shop_category_select", selectData)
                    }*/
                }
    }
}