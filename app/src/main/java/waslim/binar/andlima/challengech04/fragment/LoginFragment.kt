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
        }

//        lgn()
//        btn_login.setOnClickListener {
//            if (loginAuth()){
//                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
//            }
//        }

        masuk()


        rgst()

    }

//   private fun lgn() {
//
//////        regist = requireContext().getSharedPreferences("RGST", Context.MODE_PRIVATE)
//////
//////        val usernm = regist.getString("USR", "")
//////        val email = regist.getString("EML", "")
//////        val konfpass = regist.getString("KPS", "")
//////
//////        btn_login.setOnClickListener {
//////            val eml = masukan_email_login.text.toString()
//////            val pss = masukan_password_login.text.toString()
//////
//////            if (eml == email && pss == konfpass) {
//////                val sf = login.edit()
//////                sf.putString("USER", usernm)
//////                sf.putString("EMAIL", eml)
//////                sf.putString("PASW", pss)
//////                sf.apply()
//////                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
//////
//////            } else if (eml != email || pss != konfpass){
//////                Toast.makeText(requireContext(), "Periksa Password/Email", Toast.LENGTH_LONG).show()
//////            } else {
//////                Toast.makeText(requireContext(), "Lengkapi Data", Toast.LENGTH_LONG).show()
//////
//////            }
//////        }
////
////        val eml = masukan_email_login.text.toString()
////        val pss = masukan_password_login.text.toString()
////
////        GlobalScope.launch {
////            val pengguna = bData?.userDao()?.getPengguna(eml)
////
////            activity?.runOnUiThread {
////                if (eml == "" || pss == ""){
////                    Toast.makeText(requireContext(), "Lengkapi Data", Toast.LENGTH_LONG).show()
////                } else{
////                    Toast.makeText(requireContext(), "Lengkapi Data", Toast.LENGTH_LONG).show()
////                    val sf : SharedPreferences.Editor = login.edit()
////                    sf.putString("user", pengguna[].username)
////                    sf.apply()
////                }
////            }
////        }
//
//        val eml = masukan_email_login.text.toString()
//        val pss = masukan_password_login.text.toString()
//
//        val masuk = bData?.userDao()?.getPengguna(eml, pss)
//
//
//
//    }


//    private fun loginAuth() : Boolean{
//
//        if (masukan_email_login.text.isNotEmpty() && masukan_password_login.text.toString().isNotEmpty()){
//
//            bData = UserDatabase.getInstance(requireContext())
//
//            val eml = masukan_email_login.text.toString()
//            val pss = masukan_password_login.text.toString()
//
//            val user = bData?.userDao()?.getPengguna(eml, pss)
//
//            return if (user.isNullOrEmpty()){
//                Toast.makeText(requireContext(), "emai dan password salah", Toast.LENGTH_SHORT).show()
//                false
//            }else {
//                //add username of user in a shared preference after checking user availability
//                val sharedPreference = requireContext().getSharedPreferences("DATAUSER", Context.MODE_PRIVATE)
//                val prefs = sharedPreference.edit()
//                prefs.putString("USERNAME", user)
//                prefs.apply()
//                true
//            }
//        }else{
//            Toast.makeText(requireContext(), "email dan password harus diisi", Toast.LENGTH_SHORT).show()
//            return false
//        }
//    }

    fun masuk (){
        btn_login.setOnClickListener {
            bData = UserDatabase.getInstance(requireContext())

                val eml = masukan_email_login.text.toString()
                val pss = masukan_password_login.text.toString()

            val email = eml
            val password = pss

                val msk = bData?.userDao()?.getPengguna(email, password)

                if (eml == "" || pss == "") {
                    Toast.makeText(requireContext(), "Lengkapi Data", Toast.LENGTH_SHORT).show()
                } else if (msk.isNullOrEmpty()){
                    Toast.makeText(requireContext(), "Emai & Password Tidak Cocok", Toast.LENGTH_SHORT).show()
                } else {
                    val sUser = requireContext().getSharedPreferences("DATAUSER", Context.MODE_PRIVATE)
                    val prefs = sUser.edit()
                    prefs.putString("USERNAME", msk)
                    prefs.apply()
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