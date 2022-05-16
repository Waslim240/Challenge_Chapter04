package waslim.binar.andlima.challengech04.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*
import waslim.binar.andlima.challengech04.R
import waslim.binar.andlima.challengech04.room.user.UserDatabase


class LoginFragment : Fragment() {
    //    lateinit var login : SharedPreferences
    private var bData: UserDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireContext().getSharedPreferences("DATAUSER", Context.MODE_PRIVATE).contains("USERNAME")){
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment)
            requireActivity().finish()
        }

        masuk()
        rgst()

    }


    fun masuk (){
        btn_login.setOnClickListener {
            bData = UserDatabase.getInstance(requireContext())

                val eml = masukan_email_login.text.toString()
                val pss = masukan_password_login.text.toString()

                val msk = bData?.userDao()?.getPengguna(eml, pss)

                when {
                    eml == "" || pss == "" -> {
                        Toast.makeText(requireContext(), "Lengkapi Data", Toast.LENGTH_SHORT).show()
                    }
                    msk.isNullOrEmpty() -> {
                        Toast.makeText(requireContext(), "Emai & Password Tidak Cocok", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        val sUser = requireContext().getSharedPreferences("DATAUSER", Context.MODE_PRIVATE)
                        val prefs = sUser.edit()
                        prefs.putString("USERNAME", msk)
                        prefs.apply()
                        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                        requireActivity().finish()
                    }
                }
            }
        }


    private fun rgst(){
        belum_punya_akun.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registrasiFragment)
        }
    }
}