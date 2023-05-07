package com.solar.firebase.firestore.notice

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.solar.firebase.databinding.ItemNoticeBinding
import kotlinx.android.synthetic.main.activity_notice_write.*

class NoticeListAdapter(
    private val noticeListViewModel: NoticeListViewModel
) : ListAdapter<Notice, NoticeListViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoticeBinding.inflate(inflater, parent, false)
        return NoticeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeListViewHolder, position: Int) {
        holder.binding.root.setOnLongClickListener { showDeleteDialog(it.context, position) }
        holder.binding.root.setOnClickListener { NoticeDetailActivity.start(holder.binding.root.context, getItem(position).id) }
        holder.binding.notice = getItem(position)
    }

    private fun showDeleteDialog(context: Context, position: Int): Boolean {
        MaterialDialog(context).show {
            title(text = "삭제하시겠습니까?")
            positiveButton(text = "확인") { dialog ->
                noticeListViewModel.deleteNotice(getItem(position).id)
                dismiss()
            }
            negativeButton(text = "취소") { dialog ->
                dialog.dismiss()
            }
        }
        return true
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Notice>() {
            override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Notice, newItem: Notice): Boolean =
                oldItem == newItem
        }
    }
}

class NoticeListViewHolder(val binding: ItemNoticeBinding) :
    RecyclerView.ViewHolder(binding.root)
