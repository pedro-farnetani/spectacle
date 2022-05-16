package com.pedrofarnetani.spectacle.view.music.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedrofarnetani.spectacle.R
import com.pedrofarnetani.spectacle.models.Music

internal class MusicAdapter(
    private val onClickListener: OnClickListener,
    private val withDeleteOption: Boolean
) : ListAdapter<Music, MusicAdapter.MusicViewHolder>(MusicDiffCallback) {

    inner class MusicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val musicImageView: ImageView = view.findViewById(R.id.musicImageView)
        private val musicTitleTextView: TextView = view.findViewById(R.id.musicTitleTextView)
        private val musicSingerTextView: TextView = view.findViewById(R.id.musicSingerTextView)
        private val iconImageButton: ImageButton = view.findViewById(R.id.iconImageButton)

        fun bind(music: Music) {
            musicImageView.setBackgroundResource(R.drawable.spotify_image)
            musicTitleTextView.text = music.API
            musicSingerTextView.text = music.Description
            iconImageButton.apply {
                if (withDeleteOption) {
                    setImageResource(R.drawable.ic_delete)
                }
                setOnClickListener { onClickListener.onClick(music) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_item, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music = getItem(position)
        holder.bind(music)
    }

    class OnClickListener(val clickListener: (music: Music) -> Unit) {
        fun onClick(music: Music) = clickListener(music)
    }
}

object MusicDiffCallback : DiffUtil.ItemCallback<Music>() {
    override fun areItemsTheSame(oldItem: Music, newItem: Music): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
        return oldItem.API == newItem.API
    }
}