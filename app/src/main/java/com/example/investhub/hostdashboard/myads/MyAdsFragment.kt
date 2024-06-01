package com.example.investhub.hostdashboard.myads

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentMyAdsBinding
import com.example.investhub.investordashboard.home.adapter.ViewPagerImageAdapter
import java.io.File

class MyAdsFragment : BaseFragment<FragmentMyAdsBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentMyAdsBinding {
        return FragmentMyAdsBinding.inflate(layoutInflater)
    }

    private var imageUri: Uri?=null
    private var switch=false

    override fun initViews() {
        super.initViews()
        initCard()
        setupTransformer()
        binding.actionBar.tvTitle.text="Business Card"
        if (binding.title.isFocusable != true && binding.body.isFocusable != true && binding.footer.isFocusable != true) {
            binding.title.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "You cant edit the title text first enable the editText ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.body.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "You cant edit the body text first enable the editText ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.footer.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "You cant edit the footer text first enable the editText ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.tvEditText.setOnClickListener {
            if (binding.title.isFocusable != true) {
                binding.title.isFocusable = true
                binding.title.isFocusableInTouchMode = true
                binding.body.isFocusable = true
                binding.body.isFocusableInTouchMode = true
                binding.footer.isFocusable = true
                binding.footer.isFocusableInTouchMode = true
                Toast.makeText(requireContext(), "Now you can edit the text", Toast.LENGTH_SHORT)
                    .show()

            } else {
                Toast.makeText(requireContext(), "Your Already Enabled", Toast.LENGTH_SHORT).show()
            }

        }
        binding.tvScreenShort.setOnClickListener {
            imageUri = createImageUri()
            val viewLayout = binding.viewLayout
            val bitmap = getBitmapFromViewUsingCanvas(viewLayout)
            storeBitmap(bitmap)
            Toast.makeText(requireContext(), "Screenshot Done", Toast.LENGTH_SHORT).show()


        }
        binding.tvShare.setOnClickListener {
            if (imageUri == null) {
                Toast.makeText(
                    requireContext(),
                    "First take screenshot then share your card",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "image/*"
                    putExtra(Intent.EXTRA_STREAM, imageUri)
                }
                startActivity(Intent.createChooser(intent, ""))
            }

        }


    }

    private fun getBitmapFromViewUsingCanvas(view: View): Bitmap {
        val bitmap: Bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas: Canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun storeBitmap(bitmap: Bitmap) {
        val outputStream =
            imageUri?.let { requireContext().applicationContext.contentResolver.openOutputStream(it) }
        if (outputStream != null) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
        outputStream?.close()
    }

    private fun createImageUri(): Uri {
        val appContext = requireContext().applicationContext
        val image = File(appContext.filesDir, "poster.png")
        return FileProvider.getUriForFile(appContext, "com.example.investhub.provider", image)
    }

    fun initCard() {
        binding.viewPager2.offscreenPageLimit = 3
        binding.viewPager2.clipToPadding = false
        binding.viewPager2.clipChildren = false
        binding.viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        var imageList: List<Int>
        imageList = ArrayList<Int>()
        imageList = imageList + R.drawable.business_card_1
        imageList = imageList + R.drawable.b6
        imageList = imageList + R.drawable.b7
        imageList = imageList + R.drawable.b8
        imageList = imageList + R.drawable.b9
        binding.viewPager2.adapter = ViewPagerImageAdapter(imageList)
    }

    fun setupTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(50))
        transformer.addTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = (0.85 + r * 0.15f).toFloat()
        }
        binding.viewPager2.setPageTransformer(transformer)
    }


}