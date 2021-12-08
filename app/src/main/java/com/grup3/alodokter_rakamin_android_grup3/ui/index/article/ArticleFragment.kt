package com.grup3.alodokter_rakamin_android_grup3.ui.index.article

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.adapters.ArticleRecyclerViewAdapter
import com.grup3.alodokter_rakamin_android_grup3.adapters.SliderArticleAdapter
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentArticleBinding
import com.grup3.alodokter_rakamin_android_grup3.models.entity.SampleArticleSliderItem
import com.grup3.alodokter_rakamin_android_grup3.ui.index.IndexActivity
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations

class ArticleFragment : BaseFragment<FragmentArticleBinding>() {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentArticleBinding = FragmentArticleBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as IndexActivity).setSupportActionBar(binding.tbIndexArticle)
        setHasOptionsMenu(true)
        setupArticleSlider()
        setupSpinner()
        setupArticleList()
    }

    private fun setupArticleSlider() {
        val listArticle = listOf(
            SampleArticleSliderItem(
                "Beberapa Makanan Tinggi Antioksidan dan Jenisnya",
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1592875194/attached_image/beberapa-makanan-tinggi-antioksidan-dan-jenisnya-0-alodokter.jpg"
            ),
            SampleArticleSliderItem(
                "Kenali Mitos dan Fakta Seputar Susu UHT",
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1606454160/attached_image/susu-uht-ketahui-fakta-dan-mitosnya-di-sini-0-alodokter.jpg"
            ),
            SampleArticleSliderItem(
                "Bunda, Ini Tips Mengatasi Rasa Sakit Saat BAB setelah Melahirkan",
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1638792011/attached_image/bunda-ini-tips-mengatasi-rasa-sakit-saat-bab-setelah-melahirkan-0-alodokter.jpg"
            ),
            SampleArticleSliderItem(
                "Makanan Pendamping ASI Bisa Dimulai dengan Menu Berikut",
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1592894934/attached_image/makanan-pendamping-asi-bisa-dimulai-dengan-menu-berikut-0-alodokter.jpg"
            ),
            SampleArticleSliderItem(
                "4 Penyakit Tulang yang Jarang Diketahui",
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1638867328/attached_image/4-penyakit-tulang-yang-jarang-diketahui-0-alodokter.jpg"
            ),
            SampleArticleSliderItem(
                "Mengenali Lapisan Anatomi Kulit dan Nutrisi Penunjangnya",
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1627875763/attached_image/mengenali-lapisan-anatomi-kulit-dan-nutrisi-penunjangnya-0-alodokter.jpg"
            ),
        )
        val sliderAdapter = SliderArticleAdapter()
        sliderAdapter.setItem(listArticle)
        binding.sliderArticle.apply {
            setSliderAdapter(sliderAdapter)
            setIndicatorAnimation(IndicatorAnimationType.SLIDE)
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            startAutoCycle()
        }

        sliderAdapter.listener = { item ->
            Toast.makeText(requireActivity(), item.title, Toast.LENGTH_SHORT).show()
        }

    }

    private fun setupSpinner() {
        val arrayAdapter =
            ArrayAdapter(
                requireActivity(),
                R.layout.style_spinner,
                resources.getStringArray(R.array.category_array)
            )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = arrayAdapter
    }

    private fun setupArticleList() {
        val adapter = ArticleRecyclerViewAdapter()
        binding.rvArticle.setHasFixedSize(true)
        binding.rvArticle.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.index_article_menu, menu)
    }
}