package ru.it_cron.android1.presentation.case_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.it_cron.android1.databinding.SplashItemBinding
import ru.it_cron.android1.domain.model.ContainerImage

class ImagesViewPagerAdapter(
    private val containerData: ContainerImage,
    private val glide: RequestManager
): RecyclerView.Adapter<ImagesViewPagerAdapter.PagerViewHolderImage>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolderImage {
        val view = SplashItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PagerViewHolderImage(view)
    }

    override fun getItemCount(): Int = containerData.images.size

    override fun onBindViewHolder(holder: PagerViewHolderImage, position: Int) {
        holder.binding.flSplash.setBackgroundColor(containerData.colorId)
        holder.itemView.run {
            glide.load(containerData.images[position])
                .into(holder.binding.ivSplash)
        }
    }
    class PagerViewHolderImage(val binding: SplashItemBinding): RecyclerView.ViewHolder(binding.root)
}