package waslim.binar.andlima.challengech04.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_registrasi.*
import waslim.binar.andlima.challengech04.R


class RegistrasiFragment : Fragment() {
    lateinit var regist : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrasi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        regist = requireContext().getSharedPreferences("RGST", Context.MODE_PRIVATE)

        rgst()
    }

    private fun rgst(){
        daftar.setOnClickListener {
            val usernm = masukan_username_regist.text.toString()
            val email = masukan_email_regist.text.toString()
            val konfpass = masukan_konfirmasi_password_regist.text.toString()
            val passw = masukan_password_regist.text.toString()

            if (usernm == "" || email == "" || passw == "" ){
                Toast.makeText(requireContext(), "Lengkapi Data", Toast.LENGTH_LONG).show()
            } else if (konfpass != passw) {
                Toast.makeText(requireContext(), "Konfirmasi Password Salah", Toast.LENGTH_LONG).show()
            } else{
                val sf = regist.edit()
                sf.putString("USR", usernm)
                sf.putString("EML", email)
                sf.putString("KPS", konfpass)
                sf.putString("PSS", passw)
                sf.apply()
                Navigation.findNavController(requireView()).navigate(R.id.action_registrasiFragment_to_loginFragment)
            }
        }
    }


}