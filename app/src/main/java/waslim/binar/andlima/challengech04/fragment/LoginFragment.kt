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
import kotlinx.android.synthetic.main.fragment_login.*
import waslim.binar.andlima.challengech04.R


class LoginFragment : Fragment() {
    lateinit var regist : SharedPreferences
    lateinit var login : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireContext().getSharedPreferences("data", Context.MODE_PRIVATE).contains("EMAIL")){
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment)
        } else{
            login = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE)
            lgn()
        }

        rgst()

    }

    private fun lgn (){
        regist = requireContext().getSharedPreferences("RGST", Context.MODE_PRIVATE)

        val usernm = regist.getString("USR", "")
        val email = regist.getString("EML", "")
        val konfpass = regist.getString("KPS", "")

        btn_login.setOnClickListener {
            val eml = masukan_email_login.text.toString()
            val pss = masukan_password_login.text.toString()

            if (eml == "" && pss == "") {
                Toast.makeText(requireContext(), "Lengkapi Data", Toast.LENGTH_LONG).show()
            } else if (eml != email && pss != konfpass){
                Toast.makeText(requireContext(), "Periksa Password/Email", Toast.LENGTH_LONG).show()
            } else {
                val sf = login.edit()
                sf.putString("USER", usernm)
                sf.putString("EMAIL", eml)
                sf.putString("PASW", pss)
                sf.apply()
                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }


    private fun rgst(){
        belum_punya_akun.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registrasiFragment)
        }
    }
}