package com.example.finalapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalapplication.databinding.FragmentListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {
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
        val binding = FragmentListBinding.inflate(inflater, container, false)
        val area = arguments?.getString("area") ?: "1"

        val call: Call<ListResponse> = RetrofitConnection.listNetServ.getJsonList(
            "6u8TrayNZB5SJTA16jp8QA+Gxqnfg0+iIQ3p9BpNTvNTJglB+fxHVqibODUo1xV91MI3tJhn6d670Om3L6gGag==",
            area.toInt(),
            "Y",
            "A",
            "ETC",
            "AppTest",
            "json"
        )

        call?.enqueue(object: Callback<ListResponse> {
            override fun onResponse(call: Call<ListResponse>, response: Response<ListResponse>) {
                if (response.isSuccessful) {
                    Log.d("mobileApp", "${response.body()}")
                    binding.listRecyclerView.adapter = ListAdapter(response.body()?.response!!.body!!.items!!.item)
                    binding.listRecyclerView.layoutManager = LinearLayoutManager(activity)
                    binding.listRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
                }
            }

            override fun onFailure(call: Call<ListResponse>, t: Throwable) {
                Log.d("mobileApp","$t")
            }
        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}