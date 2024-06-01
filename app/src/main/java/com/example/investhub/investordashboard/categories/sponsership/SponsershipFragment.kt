package com.example.investhub.investordashboard.categories.sponsership
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.data.postnewad.PostNewAdDataItem
import com.example.investhub.data.sponsership.SponsershipDataItem
import com.example.investhub.databinding.FragmentSponsershipBinding
import com.example.investhub.investordashboard.categories.sponsership.SponsershipAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SponsershipFragment : BaseFragment<FragmentSponsershipBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentSponsershipBinding {
        return FragmentSponsershipBinding.inflate(layoutInflater)
    }
    lateinit var database: DatabaseReference
    private lateinit var sponsershipData:ArrayList<SponsershipDataItem>

    override fun initViews() {
        super.initViews()
        binding.toolbar.tvTitle.text="Sponsorship"
        database = FirebaseDatabase.getInstance().getReference("Sponsorship")
        getAds()
        sponsershipData= arrayListOf<SponsershipDataItem>()
    }

    fun getAds(){
        binding.rvAds.visibility= View.GONE
        binding.progress.visibility= View.VISIBLE
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                sponsershipData.clear()
                if (snapshot.exists()) {
                    for (adsSnap in snapshot.children) {
                        val itemAds = adsSnap.getValue(SponsershipDataItem::class.java)
                        if (itemAds != null) {
                            sponsershipData.add(itemAds)
                            val adapter = SponsershipAdapter(sponsershipData)
                            binding.rvAds.adapter = adapter
                            binding.rvAds.visibility= View.VISIBLE
                            binding.progress.visibility= View.GONE
                            adapter.setOnItemClickListner(object : SponsershipAdapter.onItemClickListner{
                                override fun onItemClick(position: Int) {
                                    val name = sponsershipData[position].eventName
                                    val bundle= Bundle()
                                    bundle.putString("organizationName",name)
                                    findAppMainNavControllerInvestor().navigate(R.id.SponsershipDetails,bundle)


                                }

                            })
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}