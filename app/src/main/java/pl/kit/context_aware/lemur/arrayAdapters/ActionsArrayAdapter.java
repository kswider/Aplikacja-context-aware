package pl.kit.context_aware.lemur.ArrayAdapters;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.kit.context_aware.lemur.Activities.EditScript;
import pl.kit.context_aware.lemur.DialogFragments.ActionPickerFragment;
import pl.kit.context_aware.lemur.DialogFragments.NotificationMessageDetailsFragment;
import pl.kit.context_aware.lemur.DialogFragments.SMSMessageDetailsFragment;
import pl.kit.context_aware.lemur.R;
import pl.kit.context_aware.lemur.ListItems.ActionItem;

/**
 * Created by Tomek on 2017-04-22.
 */

public class ActionsArrayAdapter extends ArrayAdapter<ActionItem>  {
    public ArrayList<ActionItem> list;
    private Context mContext;
    /**
     * Needed constructor
     */
    public ActionsArrayAdapter(Context context, ArrayList<ActionItem> Texts) {
        super(context,0, Texts);
        list = Texts;
        mContext = context;
    }

    /**
     * Method creating view of single list element
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ActionItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_element_script_edit, parent, false);
        }
        // Lookup view for data population
        TextView main = (TextView) convertView.findViewById(R.id.le_es_main);
        TextView sub = (TextView) convertView.findViewById(R.id.le_es_sub);
        // Populate the data into the template view using the data object


        ImageButton editButton = (ImageButton)convertView.findViewById(R.id.le_es_edit);
        final ImageButton deleteButton = (ImageButton)convertView.findViewById(R.id.le_es_delete);

        final int type = item.getActionType();
        if(type == ActionItem.ACTION_BLUETOOTH_OFF) {
            item.setMainText(mContext.getResources().getString(R.string.action_1));
            editButton.setVisibility(View.INVISIBLE);
        }else if(type == ActionItem.ACTION_BLUETOOTH_ON){
            item.setMainText(mContext.getResources().getString(R.string.action_2));
            editButton.setVisibility(View.INVISIBLE);
        }else if(type == ActionItem.ACTION_WIFI_ON) {
            item.setMainText(mContext.getResources().getString(R.string.action_3));
            editButton.setVisibility(View.INVISIBLE);
        }else if(type == ActionItem.ACTION_WIFI_OFF) {
            item.setMainText(mContext.getResources().getString(R.string.action_4));
            editButton.setVisibility(View.INVISIBLE);
        }else if (type == ActionItem.ACTION_SOUND_OFF) {
            item.setMainText(mContext.getResources().getString(R.string.action_5));
            editButton.setVisibility(View.INVISIBLE);
        }else if(type == ActionItem.ACTION_SOUND_ON) {
            item.setMainText(mContext.getResources().getString(R.string.action_6));
            editButton.setVisibility(View.INVISIBLE);
        }else if(type == ActionItem.ACTION_VIBRATIONS_MODE) {
            item.setMainText(mContext.getResources().getString(R.string.action_7));
            editButton.setVisibility(View.INVISIBLE);
        }else if(type == ActionItem.ACTION_SEND_SMS) {
            item.setMainText(mContext.getResources().getString(R.string.action_9));
            editButton.setVisibility(View.VISIBLE);
        }else if(type == ActionItem.ACTION_SEND_NOTIFICATION) {
            item.setMainText(mContext.getResources().getString(R.string.action_8));
            editButton.setVisibility(View.VISIBLE);
        }

        main.setText(item.getMainText());
        sub.setText(item.getSubText());

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                notifyDataSetChanged();
                EditScript.ListUtils.setDynamicHeight((ListView)((Activity)mContext).findViewById(R.id.es_lv_actions));
            }
        });
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (type == ActionItem.ACTION_SEND_SMS){
                    if (ContextCompat.checkSelfPermission(mContext,
                            Manifest.permission.SEND_SMS)
                            == PackageManager.PERMISSION_GRANTED) {
                        DialogFragment newFragment = new SMSMessageDetailsFragment();
                        ((SMSMessageDetailsFragment) newFragment).setPosition(position);
                        ((SMSMessageDetailsFragment) newFragment).setMessage(list.get(position).getSmsMessage());
                        ((SMSMessageDetailsFragment) newFragment).setPhoneNo(list.get(position).getSmsPhoneNumber());
                        newFragment.show(((Activity)mContext).getFragmentManager(), "SendSMSExtended");
                    } else {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.pl_send_sms), Toast.LENGTH_SHORT).show();
                    }
                }else if(type == ActionItem.ACTION_SEND_NOTIFICATION){
                    DialogFragment newFragment = new NotificationMessageDetailsFragment();
                    ((NotificationMessageDetailsFragment) newFragment).setPosition(position);
                    ((NotificationMessageDetailsFragment) newFragment).setMessage(list.get(position).getNotificationMessage());
                    ((NotificationMessageDetailsFragment) newFragment).setnotiTitle(list.get(position).getNotificationTitle());
                    newFragment.show(((Activity)mContext).getFragmentManager(), "NotificationExtended");
                }

            }
        });


        // Return the completed view to render on screen
        return convertView;
    }
}
