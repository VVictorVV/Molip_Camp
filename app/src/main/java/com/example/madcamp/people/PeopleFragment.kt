package com.example.madcamp.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.madcamp.R
import com.example.madcamp.databinding.FragmentPeopleBinding

class PeopleFragment : Fragment() {

    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)

        // + 버튼 클릭 시 PeopleInputFragment로 이동
        binding.btnAdd.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.bottom_layout, PeopleInputFragment())
                .addToBackStack(null)
                .commit()
        }

        // 사람 목록 RecyclerView 설정
        val peopleList = PeopleManager.getPeople() // 여기에 현재 등록된 사람 목록이 있다고 가정
        val adapter = PeopleAdapter(peopleList) { selectedPerson ->
            val fragment = PeopleDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("person", selectedPerson)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.bottom_layout, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.rvPeopleList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvPeopleList.adapter = adapter

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
