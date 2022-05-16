package waslim.binar.andlima.challengech04.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_registrasi.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import waslim.binar.andlima.challengech04.R
import waslim.binar.andlima.challengech04.room.user.User
import waslim.binar.andlima.challengech04.room.user.UserDatabase


class RegistrasiFragment : Fragment() {
//    lateinit var regist : SharedPreferences
    private var dataBase : UserDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrasi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        daftar()

    }

    private fun daftar(){
        daftar.setOnClickListener {
            if (masukan_username_regist.text.toString() == "" || masukan_email_regist.text.toString() == "" ||
                masukan_konfirmasi_password_regist.text.toString() == "" || masukan_password_regist.text.toString() == ""){
                Toast.makeText(requireContext(), "Lengkapi Data", Toast.LENGTH_LONG).show()
            } else if (masukan_konfirmasi_password_regist.text.toString() !=  masukan_password_regist.text.toString()){
                Toast.makeText(requireContext(), "Password & Konfirmasi Password Tidak Sesuai", Toast.LENGTH_LONG).show()
            } else{
                prosesRgst()
                Toast.makeText(requireContext(), "Pendaftaran Berhasil", Toast.LENGTH_LONG).show()
                Navigation.findNavController(requireView()).navigate(R.id.action_registrasiFragment_to_loginFragment)
                requireActivity().finish()
            }
        }
    }

    private fun prosesRgst(){

        dataBase = UserDatabase.getInstance(requireContext())

        GlobalScope.async {
            val user = masukan_username_regist.text.toString()
            val email = masukan_email_regist.text.toString()
            val konPass = masukan_konfirmasi_password_regist.text.toString()
            val pass = masukan_password_regist.text.toString()

            val regist = dataBase?.userDao()?.insertUser(User(null, email, user, pass))

            activity?.runOnUiThread {
                if (regist != 0.toLong()){
                    Toast.makeText(requireContext(), "Pendaftaran telah berhasil", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Pendaftaran Gagal", Toast.LENGTH_LONG).show()
                }
            }
        }
    }




}