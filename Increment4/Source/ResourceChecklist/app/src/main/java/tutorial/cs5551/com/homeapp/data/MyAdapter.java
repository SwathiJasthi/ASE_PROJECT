package tutorial.cs5551.com.homeapp.data;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tutorial.cs5551.com.homeapp.R;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {
    private ArrayList<Task> tasklist = new ArrayList<Task>();
    private ArrayList<Task> templist = new ArrayList<Task>();
    private OnItemClickListener mOnItemClickListener;
    //private Cursor mCursor;

    public MyAdapter(ArrayList<Task> tasklist) {
        this.tasklist = tasklist;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem, parent, false);

        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Task task = getItem(position);
        // tasklist.add(task);
        // Task task = tasklist.get(position);
        holder.txtTitle.setText(task.itemname);
        if (task.hasDueDate()) {
            CharSequence formatteddate = DateFormat.format("MM/dd/yyyy", task.duedate);
            holder.duedate.setText(formatteddate);
        } else {
            holder.duedate.setText("");
        }
        if (task.iscompleted) {
            holder.completedtv.setText("Completed");
            holder.cb.setChecked(true);
        } else {
            holder.completedtv.setText("Pending");
            holder.cb.setChecked(false);

        }
        holder.txtbrand.setText(task.itembrand);
        holder.personname.setText(task.bname);
        holder.alarmIV.setImageResource(R.drawable.delete);

    }

    @Override
    public int getItemCount() {
        return tasklist.size();
    }

  /*  public void filter(String text) {
        templist.clear();
        if (text.isEmpty()) {
            templist.addAll(tasklist);
        } else {
            text = text.toLowerCase();
            for (Task item : tasklist) {
                if (item.bname.toLowerCase().contains(text)) {
                    templist.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }*/

    private void completionToggled(Holder holder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemToggled(holder.cb.isChecked(), holder.getAdapterPosition());
            //setStrikeThroughView(holder.nameView, holder.checkBox.isChecked());
            if (holder.cb.isChecked()) {
                holder.completedtv.setText("Completed");
                holder.cb.setChecked(true);

            } else if (!holder.cb.isChecked()) {
                holder.completedtv.setText("Pending");
                holder.cb.setChecked(false);
                //holder.nameView.setState(TaskTitleView.NORMAL);
                /*Task taskk = new Task(mCursor);
                if (taskk.hasDueDate()) {
                    Calendar c = Calendar.getInstance();
                    long currentdate = c.getTimeInMillis();
                    if (currentdate > taskk.dueDateMillis) {
                        holder.nameView.setState(TaskTitleView.OVERDUE);
                    }
                }*/
            }
        }
    }

    private void postItemClick(Holder holder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(holder.itemView, holder.getAdapterPosition());
        }
    }

    public Task getItem(int position) {

        return tasklist.get(position);
    }


    @Override
    public long getItemId(int position) {
        return getItem(position).id;
    }


    /* Callback for list item click events */
    public interface OnItemClickListener {
        void onItemClick(View v, int position);

        void onItemToggled(boolean active, int position);
    }

    /* ViewHolder for each task item */
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox cb;
        TextView completedtv;
        TextView txtTitle;
        TextView txtbrand;
        TextView personname;
        TextView duedate;
        ImageView alarmIV;

        public Holder(View row) {
            super(row);

            txtTitle = (TextView) row.findViewById(R.id.itemnameTV);
            txtbrand = (TextView) row.findViewById(R.id.itembrandTV);
            personname = (TextView) row.findViewById(R.id.pnameTV);
            duedate = (TextView) row.findViewById(R.id.dateTV);
            completedtv = (TextView) row.findViewById(R.id.completedIV);
            alarmIV = (ImageView) row.findViewById(R.id.alarmimage);
            cb = (CheckBox) row.findViewById(R.id.checkboxxxxxxx);

            alarmIV.setOnClickListener(this);
            cb.setOnClickListener(this);
            // checkBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == cb) {
                completionToggled(this);
            } else {
                postItemClick(this);
            }
        }
    }


}
