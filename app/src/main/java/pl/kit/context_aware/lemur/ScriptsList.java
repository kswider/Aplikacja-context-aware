package pl.kit.context_aware.lemur;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.kit.context_aware.lemur.FilesOperations.FilesOperations;
import pl.kit.context_aware.lemur.Readers.ReadTime;
import pl.kit.context_aware.lemur.TmpTests.ListItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScriptsList extends ListFragment {
    ArrayList<ListItem> ItemList;

    public ScriptsList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FloatingActionButton fab =
                (FloatingActionButton) inflater.inflate(R.layout.fragment_scripts_list, container, false)
                        .findViewById(R.id.floatingButton);

        return inflater.inflate(R.layout.fragment_scripts_list, container, false);
    }

    @Override
    public void onResume() {
        ItemList = new ArrayList<ListItem>();

        for(String scriptName : FilesOperations.getAllModelNames(this.getContext())){
            ItemList.add(new ListItem(scriptName,"---"));
        }

        final EditScriptArrayAdapter adapter = new EditScriptArrayAdapter(this.getContext(), ItemList);

        setListAdapter(adapter);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> av, View v, int position, long id) {
                DialogFragment newFragment = new DeleteScriptFragment();
                ((DeleteScriptFragment) newFragment).setFileName(ItemList.get(position).mainText);
                newFragment.show(getActivity().getFragmentManager(),"123");
                return true;
            }
        });
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ItemList = new ArrayList<ListItem>();

        for(String scriptName : FilesOperations.getAllModelNames(this.getContext())){
            ItemList.add(new ListItem(scriptName,"---"));
        }

        final EditScriptArrayAdapter adapter = new EditScriptArrayAdapter(this.getContext(), ItemList);

        setListAdapter(adapter);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> av, View v, int position, long id) {
                DialogFragment newFragment = new DeleteScriptFragment();
                ((DeleteScriptFragment) newFragment).setFileName(ItemList.get(position).mainText);
                newFragment.show(getActivity().getFragmentManager(),"Delete Script");
                return true;
            }
        });

    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Bundle eFileName = new Bundle();
        eFileName.putString("eFileName",ItemList.get(position).mainText);

        Intent intent = new Intent(v.getContext(),EditScript.class);
        intent.putExtras(eFileName);
        startActivity(intent);
    }


}
