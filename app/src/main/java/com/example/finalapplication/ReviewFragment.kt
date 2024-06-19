package com.example.finalapplication

import android.content.Context
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalapplication.databinding.FragmentIntroBinding
import com.example.finalapplication.databinding.FragmentReviewBinding
import com.example.finalapplication.databinding.ItemReviewBinding
import com.google.android.play.integrity.internal.f
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ReviewViewHolder(val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root)

class ReviewAdapter(val context: Context, val itemList: MutableList<ReviewData>): RecyclerView.Adapter<ReviewViewHolder>() {
    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ReviewViewHolder(ItemReviewBinding.inflate(layoutInflater))
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val data = itemList.get(position)

        holder.binding.run {
            title.text = data.title
            ratingBar.rating = data.stars.toFloat()
            commment.text = data.comments
        }

        val imageRef = MyApplication.storage.reference.child("images/${data.docId}.jpg")
        imageRef.downloadUrl.addOnCompleteListener{ task->
            if(task.isSuccessful) {
                holder.binding.imageView.visibility = View.VISIBLE
                Glide.with(context)
                    .load(task.result)
                    .into(holder.binding.imageView)
            }
        }

    }
}

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    lateinit var uri: Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentReviewBinding.inflate(inflater, container, false)
        val contentid = arguments?.getString("contentid") ?: "1"

        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode === android.app.Activity.RESULT_OK) {
                binding.addImage.visibility = View.VISIBLE
                Glide
                    .with(requireContext())
                    .load(it.data?.data)
                    .override(100,100)
                    .into(binding.addImage)
                uri = it.data?.data!!
            }
        }
        binding.btnImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            requestLauncher.launch(intent)
        }

        binding.btnReview.setOnClickListener {
            if(MyApplication.checkAuth()) {
                if (binding.editReview.text.isNotEmpty()) {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                    val data = mapOf(
                        "email" to MyApplication.email,
                        "title" to binding.editTitle.text.toString(),
                        "stars" to binding.ratingBar.rating.toFloat(),
                        "comments" to binding.editReview.text.toString(),
                        "date_time" to dateFormat.format(System.currentTimeMillis()),
                        "contentid" to contentid

                    )
                    MyApplication.db.collection("reviews")
                        .add(data)
                        .addOnSuccessListener {
                            Toast.makeText(context, "후기를 남겼습니다!", Toast.LENGTH_LONG).show()
                            uploadImage(it.id)
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "실패", Toast.LENGTH_LONG).show()
                        }
                }
            }
            else {
                Toast.makeText(context, "로그인 해주세요!", Toast.LENGTH_LONG).show()
            }
        }

        MyApplication.db.collection("reviews")
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ReviewData>()
                for(document in result) {
                    val item = document.toObject(ReviewData::class.java)
                    item.docId = document.id
                    if(contentid == item.contentid) {
                        itemList.add(item)
                    }
                    binding.reviewRecyclerView.adapter = ReviewAdapter(requireContext(), itemList)
                    binding.reviewRecyclerView.layoutManager = LinearLayoutManager(activity)
                }
            }
            .addOnFailureListener {
            }

        return binding.root

    }

    fun uploadImage(docId: String) {
        val imageRef = MyApplication.storage.reference.child("images/${docId}.jpg")
        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            Toast.makeText(context, "사진을 올렸습니다!", Toast.LENGTH_LONG).show()
        }
        uploadTask.addOnFailureListener {
            Toast.makeText(context, "실패", Toast.LENGTH_LONG).show()

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReviewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReviewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}