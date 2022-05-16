package waslim.binar.andlima.challengech04.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.custom_dialog_save.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import waslim.binar.andlima.challengech04.R
import waslim.binar.andlima.challengech04.adapter.AdapterNoteTaking
import waslim.binar.andlima.challengech04.room.note.NoteTaking
import waslim.binar.andlima.challengech04.room.note.NoteTakingDatabase

class HomeFragment : Fragment() {
    private var dB : NoteTakingDatabase? = null
    lateinit var login : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dB = NoteTakingDatabase.getInstance(requireContext())

        login = requireContext().getSharedPreferences("DATAUSER", Context.MODE_PRIVATE)

        val ambilUsername = login.getString("USERNAME", "")
        welcome_username.text = "Welcome, $ambilUsername"


        lgt()
        getDataNote()
        add()
    }

    private fun lgt (){
        logout.setOnClickListener {
            val al = AlertDialog.Builder(requireContext())
            al.setTitle("Konfirmasi Logout")
            al.setMessage("Anda Yakin Ingin Logout ?")
            al.setCancelable(false)
            val algt = al.create()

            al.setPositiveButton("Ya"){ dialogInterface: DialogInterface, i: Int ->
                val sf = login.edit()
                sf.clear()
                sf.apply()
                Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment)
                Toast.makeText(requireContext(), "Berhasil Logout", Toast.LENGTH_LONG).show()
                requireActivity().finish()
            } .setNegativeButton("Tidak"){ dialogInterface: DialogInterface, i: Int ->
                algt.dismiss()
                Toast.makeText(requireContext(), "Batal Logout", Toast.LENGTH_LONG).show()
            }

            al.show()
        }
    }

    private fun add () {
        fab_add.setOnClickListener {
            val alertA = LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog_save, null, false)
            val alertB = AlertDialog.Builder(requireContext())
                .setView(alertA)
                .create()

            alertA.btn_save.setOnClickListener {

                GlobalScope.async {
                    val jdl = alertA.masukan_judul_save.text.toString()
                    val ctt = alertA.masukan_catatan_save.text.toString()

                    val result = dB?.noteTakingDao()?.insertNoteTaking(NoteTaking(null, jdl, ctt))

                    requireActivity().runOnUiThread {
                        if (result != 0.toLong()){
                            Toast.makeText(requireContext(), "Berhasil Menambahkan", Toast.LENGTH_LONG).show()
                            alertB.dismiss()
                        } else{
                            Toast.makeText(requireContext(), "Gagal Menambahkan", Toast.LENGTH_LONG).show()
                        }
                        activity?.recreate()

                    }
                }
            }
            alertB.show()

        }
    }

    fun getDataNote() {
        rv_note_taking.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        GlobalScope.launch {
            val listD = dB?.noteTakingDao()?.getAllNoteTaking()

            activity?.runOnUiThread {
                listD.let {
                    val adp = AdapterNoteTaking(it!!)
                    rv_note_taking.adapter = adp
                }
            }
        }
    }



}