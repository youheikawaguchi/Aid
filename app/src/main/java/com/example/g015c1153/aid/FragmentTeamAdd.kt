package com.example.g015c1153.aid

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.fragment_team_add.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Team.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Team.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TeamAdd : Fragment(){

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
    private val teamAdapter = moshi.adapter(TeamData::class.java)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_team_add, container, false) as View

        initSpinner()

        //チーム作成ボタンを押したときの処理
        view.findViewById<Button>(R.id.teamAddButton).setOnClickListener{
            //入力内容の取得
            val editTeamName: String = editTeamName.text.toString()
            val editTeamDetail: String = editTeamDetail.text.toString()
            val editTeamLocal: String = local_spinner.selectedItem.toString()

            //チーム用のデータクラスに登録
            val teamDataAdd = TeamData()
            teamDataAdd.teamName = editTeamName
            teamDataAdd.teamDetail = editTeamDetail
            teamDataAdd.teamLocal = editTeamLocal
            //Realmに登録後、チームのIDを取得
            val teamID = RealmDAO().teamAddRealm(teamDataAdd)
            //チームをIDをもとに検索し、データベースの登録情報を取得。
            val team = RealmDAO().teamReadRealm(teamID)
            //次ページに値を渡せるように、Json文字列に変換
            val teamJson = teamAdapter.toJson(team)

            //次ページに遷移
        }
        return view
    }

    private fun initSpinner(){
        val adapter = ArrayAdapter.createFromResource(
                activity!!.baseContext,
                R.array.local_list,
                android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Team.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TeamAdd().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}