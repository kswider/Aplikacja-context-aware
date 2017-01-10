package pl.kit.context_aware.lemur;


import android.app.DialogFragment;
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

import pl.kit.context_aware.lemur.Readers.ReadTime;
import pl.kit.context_aware.lemur.TmpTests.ListItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScriptsList extends ListFragment {


    public ScriptsList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FloatingActionButton fab =
                (FloatingActionButton) inflater.inflate(R.layout.fragment_scripts_list, container, false)
                        .findViewById(R.id.floatingButton);

        /*ListView lista = (ListView) container.findViewById(R.id.ScriptsListListView);

        String mainText[] = {getResources().getString(R.string.es_SetTime),getResources().getString(R.string.es_SetDate),getResources().getString(R.string.es_SetLocation),getResources().getString(R.string.es_SetAction)};
        String subText[] = {"---","---","---","---"};

        ArrayList<ListItem> ItemList = new ArrayList<ListItem>();

        for (int i = 0; i < mainText.length; i++){
            ItemList.add(new ListItem(mainText[i],subText[i]));
        }

        final EditScriptArrayAdapter adapter = new EditScriptArrayAdapter(this.getContext(), ItemList);
        lista.setAdapter(adapter);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(position == 0) {
                    Toast.makeText(view.getContext().getApplicationContext(),
                            "Replace Me with setting day of the week window", Toast.LENGTH_SHORT)
                            .show();
                }
                else if (position == 1){
                    Toast.makeText(view.getContext().getApplicationContext(),
                            "Replace Me with setting day of the week window", Toast.LENGTH_SHORT)
                            .show();
                }
                else if (position == 2){
                    Toast.makeText(view.getContext().getApplicationContext(),
                            "Replace Me with setting location window", Toast.LENGTH_SHORT)
                            .show();
                }
                else if (position == 3){
                    Toast.makeText(view.getContext().getApplicationContext(),
                            "Replace Me with setting action window", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });*/

        return inflater.inflate(R.layout.fragment_scripts_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String mainText[] = {"Skrypt 1", "Skrypt2", "Skrypt3", "Skrypt4"};
        String subText[] = {"---","---","---","---"};

        ArrayList<ListItem> ItemList = new ArrayList<ListItem>();

        for (int i = 0; i < mainText.length; i++){
            ItemList.add(new ListItem(mainText[i],subText[i]));
        }

        final EditScriptArrayAdapter adapter = new EditScriptArrayAdapter(this.getContext(), ItemList);

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(position == 0) {
            Toast.makeText(getContext().getApplicationContext(),
                    "Replace Me with setting day of the week window", Toast.LENGTH_SHORT)
                    .show();
        }
        else if (position == 1){
            Toast.makeText(getContext().getApplicationContext(),
                    "Replace Me with setting day of the week window", Toast.LENGTH_SHORT)
                    .show();
        }
        else if (position == 2){
            Toast.makeText(getContext().getApplicationContext(),
                    "Replace Me with setting location window", Toast.LENGTH_SHORT)
                    .show();
        }
        else if (position == 3){
            Toast.makeText(getContext().getApplicationContext(),
                    "Replace Me with setting action window", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
