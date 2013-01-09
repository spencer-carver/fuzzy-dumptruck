package edu.rosehulman.lab8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView mTaskListView;
	private List<Task> mTasks;
	private ArrayAdapter<Task> mTaskAdapter;
	private DBAdapter mDbAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTaskListView = (ListView) findViewById(R.id.display_tasks_task_list);
		mDbAdapter = new DBAdapter(this);
		mDbAdapter.open();
		mTasks = mDbAdapter.getAllTasks();
		deadlineSort();
		mTaskAdapter = new ArrayAdapter<Task>(this,
				android.R.layout.simple_list_item_1, mTasks);
		mTaskListView.setAdapter(mTaskAdapter);
		mTaskListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				deleteTask(mTasks.get(position));
				return false;
			}

		});
	}
	
	private void deadlineSort() {
		List<Task> temp = mTasks;
		ArrayList<Task> temp2 = new ArrayList<Task>();
		while (!temp.isEmpty()) {
			int maxIndex = 0;
			long dateTime = Long.MAX_VALUE;
			for (int i=0; i < temp.size(); i++) {
				if (temp.get(i).getDateDue().getTimeInMillis() < dateTime) {
					maxIndex = i;
					dateTime = temp.get(i).getDateDue().getTimeInMillis();
				}
			}
			temp2.add(temp.remove(maxIndex));
		}
		mTasks = temp2;
	}

	private void addTask(Task task) {
		Toast.makeText(this, "Adding task: " + task, Toast.LENGTH_SHORT).show();
		mDbAdapter.addTask(task);
		mTasks.add(task);
		Collections.sort(mTasks);
		mTaskAdapter.notifyDataSetChanged();
	}

	private void deleteTask(Task task) {
		Toast.makeText(this, "Deleting task: " + task, Toast.LENGTH_SHORT)
				.show();
		mDbAdapter.deleteTask(task);
		mTasks.remove(task);
		mTaskAdapter.notifyDataSetChanged();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		super.onCreateDialog(id);
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.create_dialog);
		dialog.setTitle(R.string.add_task);
		final EditText courseText = (EditText) dialog
				.findViewById(R.id.create_dialog_course);
		final EditText nameText = (EditText) dialog
				.findViewById(R.id.create_dialog_name);
		final DatePicker datePicker = (DatePicker) dialog
				.findViewById(R.id.create_dialog_date);
		final Button addButton = (Button) dialog
				.findViewById(R.id.create_dialog_add_button);
		final Button cancelButton = (Button) dialog
				.findViewById(R.id.create_dialog_cancel_button);
		addButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Task task = new Task();
				task.setName(nameText.getText().toString());
				task.setCourse(courseText.getText().toString());
				task.setDateDue(datePicker.getYear(),
						datePicker.getMonth(), datePicker.getDayOfMonth());
				addTask(task);
				courseText.setText("");
				nameText.setText("");
				dialog.dismiss();
			}
		});
		cancelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		return dialog;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		if (item.getItemId() == R.id.menu_add_task) {
			showDialog(0); // Cheap, but we always show the same dialog anywway
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mDbAdapter.close();
	}
}
