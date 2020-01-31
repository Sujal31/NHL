package com.foko.nhl.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.foko.Adapters.PlayerAdapter;
import com.foko.nhl.MainActivity;
import com.foko.nhl.R;
import com.foko.nhl.interfaces.MenuItemClickListener;
import com.foko.pojo.roster.Person;
import com.foko.pojo.roster.Roster;
import com.foko.pojo.roster.RosterList;
import com.foko.utilities.GetTeamDataService;
import com.foko.utilities.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.foko.Adapters.PlayerAdapter.*;
import static java.util.Collections.*;


public class PlayerListFragment extends Fragment implements ItemClickListener, MenuItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String TAG = "PlayerListFragment";
    private static int ID = 1;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;


    private OnFragmentInteractionListener mListener;
    private PlayerAdapter mAdapter;

    public PlayerListFragment() {
        // Required empty public constructor
    }

    public static PlayerListFragment newInstance(int id) {
        PlayerListFragment fragment = new PlayerListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ID = getArguments().getInt(ARG_PARAM1);
        }

        ((MainActivity)getActivity()).setMenuItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_player_list, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        recyclerView = view.findViewById(R.id.list_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        getRosterListApi();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void OnItemClick(int id) {
        Log.d(TAG,"Player id : "+ id);
        mListener.onItemClickListener(id);
    }

    @Override
    public void nameSort() {
        sort(rosterArrayList);
        if(mAdapter != null){
            mAdapter.addDataSet(rosterArrayList);
        }
    }

    public interface OnFragmentInteractionListener {
        void onItemClickListener(int id);
    }

    List<Roster> rosterArrayList =  new ArrayList<>();

    // Get Roster list api call
    private void getRosterListApi(){
        GetTeamDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetTeamDataService.class);
        Call<RosterList> call = service.getRosterList(ID);
        call.enqueue(new Callback<RosterList>() {
            @Override
            public void onResponse(Call<RosterList> call, Response<RosterList> response) {
                if(response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    generateRosterList(response.body());
                    rosterArrayList = response.body().getRoster();
                }
            }

            @Override
            public void onFailure(Call<RosterList> call, Throwable t) {
                progressDialog.dismiss();
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void generateRosterList(RosterList response){
        List<Roster> adapterData = response.getRoster();
        if(adapterData.size() > 0){
            mAdapter = new PlayerAdapter(getContext());
            mAdapter.addDataSet(adapterData);
            mAdapter.setListener(this);
            recyclerView.setAdapter(mAdapter);
        }
    }


}
