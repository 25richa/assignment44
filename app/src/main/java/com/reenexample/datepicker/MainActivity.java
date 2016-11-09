package com.reenexample.datepicker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    private ImageView calandarImage;
    private ImageView clockImage;
    private TextView date;
    private TextView time;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private EditText title;
    private EditText details;
    private Button saveBtn;
    private Paint p = new Paint();
    private View view;
    String mDrawableName = "1";

    RecyclerView rv;
    MyAdapter adapter;
    ItemTouchHelperAdapter mAdapter;
    ArrayList<NewTodo> newtodos=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               showDialog();


            }
        });


        rv = (RecyclerView) findViewById(R.id.mRecycler);
        //SET PROPS
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        //ADAPTER
        adapter = new MyAdapter(this, newtodos);
        initSwipe();


        //RETRIEVE
        retrieve();

        /*SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(rv,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipeLeft(int position) {
                                return true;
                            }

                            @Override
                            public boolean canSwipeRight(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {


                                for (int position : reverseSortedPositions) {
                                    newtodos.remove(position);
                                    adapter.notifyItemRemoved(position);
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    newtodos.remove(position);
                                    adapter.notifyItemRemoved(position);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });

        rv.addOnItemTouchListener(swipeTouchListener);*/



    }

    private void showDialog(){

        builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.alert_dialog);


        dialog = builder.create();

        dialog.show();

        title = (EditText) dialog.findViewById(R.id.titleText);
        details = (EditText) dialog.findViewById(R.id.detailsText);
        calandarImage = (ImageView) dialog.findViewById(R.id.calendar);
        date = (TextView) dialog.findViewById(R.id.dateText);
        time = (TextView) dialog.findViewById(R.id.timeText);
        saveBtn = (Button) dialog.findViewById(R.id.buttonAdd);


        calandarImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                        /*DialogFragment dialogfragment = new DatePickerDialogClass();

                        dialogfragment.show(getFragmentManager(), "Date Picker Dialog");*/

                DialogFragment newFragment = new DatePickerDialogClass() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        date.setText("" + day + "/" + month + "/" + year);
                    }
                };
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });


        clockImage = (ImageView) dialog.findViewById(R.id.clockImage);
        clockImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment() {
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + (minute + 1));
                    }

                };
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(date.getText().toString(), time.getText().toString(), title.getText().toString(), details.getText().toString());
            }
        });


    }




    private void initSwipe() {
        Toast.makeText(getApplicationContext(), "hy", Toast.LENGTH_LONG);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    Toast.makeText(getApplicationContext(), "hy", Toast.LENGTH_LONG);
                    adapter.removeItem(position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    /*if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.calendar);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }*/
                    if(dX<=0) {
                        p.setColor(Color.parseColor("#4CAF50"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.done);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rv);
    }


    private void save(String Date,String Time, String Title, String Details)
    {
        DBAdapter db=new DBAdapter(this);
        //OPEN DB
        db.openDB();
        //COMMIT
        long result=db.add(Date,Time,Title,Details);
        if(result>0)
        {
            date.setText("");
            time.setText("");
            title.setText("");
            details.setText("");
        }else
        {
            Snackbar.make(title,"Unable To Save",Snackbar.LENGTH_SHORT).show();
        }
        db.closeDB();
        //REFRESH
        retrieve();
    }


    private void retrieve()
    {
        newtodos.clear();
        DBAdapter db=new DBAdapter(this);
        db.openDB();
        //RETRIEVE
        Cursor c=db.getAllItems();
        //LOOP AND ADD TO ARRAYLIST
        while (c.moveToNext())
        {
            int id=c.getInt(0);
            String date=c.getString(1);
            String time=c.getString(2);
            String title = c.getString(3);
            String details = c.getString(4);
            NewTodo p=new NewTodo(id,date,time,title,details);
            //ADD TO ARRAYLIS
            newtodos.add(p);
        }
        //CHECK IF ARRAYLIST ISNT EMPTY
        if(!(newtodos.size()<1))
        {
            rv.setAdapter(adapter);
        }
        db.closeDB();;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        else if(id == R.id.add_new){
            showDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    public static class DatePickerDialogClass extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(), this,year,month,day);

            return datepickerdialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
        }
    }


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        }
    }


}
