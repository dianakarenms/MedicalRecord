package com.medicalrecord.adapters

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicalrecord.calcprenatal.R
import com.medicalrecord.data.RefValue
import com.medicalrecord.data.ViewType
import com.medicalrecord.utils.Constants
import com.medicalrecord.utils.decimalsFormat
import kotlinx.android.synthetic.main.item_ref_value.view.*


class ExpandableValuesAdapter(private val listener: (RefValue, Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>(), SectionHeaderViewHolder.HeaderViewHolderCallback {

    private val USER_TYPE = 1
    private val HEADER_TYPE = 2

    private var usersList: List<RefValue>? = null
    private var userTypeList: List<String>? = null

    private lateinit var viewTypes: SparseArray<ViewType>
    private lateinit var headerExpandTracker: SparseIntArray

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View
        return when(viewType) {
            USER_TYPE -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_ref_value, parent, false)
                UserViewHolder(view)
            }
            HEADER_TYPE -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_ref_value_header, parent, false)
                SectionHeaderViewHolder(view, this)

            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_ref_value, parent, false)
                UserViewHolder(view)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        val viewType = viewTypes.get(position)
        if (itemViewType == USER_TYPE) {
            bindUserViewHolder(holder, viewType)
        } else {
            bindHeaderViewHolder(holder, position, viewType)
        }
    }

    private fun bindHeaderViewHolder(holder: RecyclerView.ViewHolder, position: Int, viewType: ViewType) {
        val dataIndex = viewType.dataIndex
        val headerViewHolder = holder as SectionHeaderViewHolder
        if (userTypeList != null) {
            headerViewHolder.sectionTitle.text = userTypeList!![dataIndex]
        }
        if (isExpanded(position)) {
            headerViewHolder.sectionTitle
                    .setCompoundDrawablesWithIntrinsicBounds(null, null, headerViewHolder.arrowUp, null)
        } else {
            headerViewHolder.sectionTitle
                    .setCompoundDrawablesWithIntrinsicBounds(null, null, headerViewHolder.arrowDown, null)
        }
    }

    private fun bindUserViewHolder(holder: RecyclerView.ViewHolder, viewType: ViewType) {
        val dataIndex = viewType.dataIndex
        (holder as UserViewHolder).bind(usersList!![dataIndex], dataIndex, listener)
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: RefValue, position: Int, listener: (RefValue, Int) -> Unit) = with(itemView) {
            val name = Constants.displayNames[item.name]
            itemRefValueNameTxt.text = name
            itemRefValueValueTxt.text = item.value.decimalsFormat(2)
            setOnClickListener { listener(item, position) }
        }
    }

    override fun getItemCount(): Int {
        var count = 0
        if (userTypeList != null && usersList != null) {
            viewTypes.clear()
            var collapsedCount = 0
            for (i in 0 until userTypeList!!.size) {
                viewTypes.put(count, ViewType(i, HEADER_TYPE))
                count += 1
                val userType = userTypeList!![i]
                val childCount = getChildCount(userType)
                if (headerExpandTracker.get(i) !== 0) {
                    // Expanded State
                    for (j in 0 until childCount) {
                        viewTypes.put(count, ViewType(count - (i + 1) + collapsedCount, USER_TYPE))
                        count += 1
                    }
                } else {
                    // Collapsed
                    collapsedCount += childCount
                }
            }
        }
        return count
    }

    override fun getItemViewType(position: Int): Int {
        return if (viewTypes.get(position).type === HEADER_TYPE) {
            HEADER_TYPE
        } else {
            USER_TYPE
        }
    }

    private fun getChildCount(type: String): Int {
        return when (type) {
            Constants.SOLUTION -> 19
            Constants.ADDITIONAL_INFO -> 0
            Constants.DOCTOR_REFERENCE -> 0
            else -> 0
        }
    }


    fun setUserListAndType(usersList: List<RefValue>?, userTypeList: List<String>?) {
        if (usersList != null && userTypeList != null) {
            this.usersList = usersList
            this.userTypeList = userTypeList
            viewTypes = SparseArray(usersList.size + userTypeList.size)
            headerExpandTracker = SparseIntArray(userTypeList.size)
            notifyDataSetChanged()
        }
    }

    override fun onHeaderClick(position: Int) {
        val viewType = viewTypes.get(position)
        val dataIndex = viewType.dataIndex
        val userType = userTypeList?.get(dataIndex)
        val childCount = getChildCount(userType!!)
        if (headerExpandTracker.get(dataIndex) === 0) {
            // Collapsed. Now expand it
            headerExpandTracker.put(dataIndex, 1)
            notifyItemRangeInserted(position + 1, childCount)
        } else {
            // Expanded. Now collapse it
            headerExpandTracker.put(dataIndex, 0)
            notifyItemRangeRemoved(position + 1, childCount)
        }
    }

    override fun isExpanded(position: Int): Boolean {
        val dataIndex = viewTypes.get(position).dataIndex
        return headerExpandTracker.get(dataIndex) === 1
    }
}



