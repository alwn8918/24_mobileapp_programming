package com.example.finalapplication

import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

class ReviewAdapter(val itemList: MutableList<ReviewData>): RecyclerView.Adapter<ReviewViewHolder>() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentReviewBinding.inflate(inflater, container, false)
        val contentid = arguments?.getString("contentid") ?: "1"

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
                    if(contentid == item.contentid) {
                        itemList.add(item)
                    }
                    binding.reviewRecyclerView.adapter = ReviewAdapter(itemList)
                    binding.reviewRecyclerView.layoutManager = LinearLayoutManager(activity)
                }
            }
            .addOnFailureListener {
            }

        return binding.root

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