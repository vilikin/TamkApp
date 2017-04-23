package in.vilik.tamkapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import in.vilik.tamkapp.timetable.notes.Note;
import in.vilik.tamkapp.timetable.notes.NoteStorage;
import in.vilik.tamkapp.utils.DateUtil;

public class NoteActivity extends AppCompatActivity {
    Calendar calendar;
    EditText nameField;
    EditText timeField;
    EditText dateField;

    Note.NoteType type;

    Spinner spinner;

    boolean inputValid;

    boolean fullDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            calendar = (Calendar) extras.getSerializable("date");

            fullDay = extras.getBoolean("fullDay", true);

            type = Note.NoteType.values()[extras.getInt("type")];
        }

        inputValid = false;

        setContentView(R.layout.activity_note);

        spinner = (Spinner) findViewById(R.id.noteTypeSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.noteTypes, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = Note.NoteType.values()[position];
                updateType();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        nameField = (EditText) findViewById(R.id.note_input_name);
        dateField = (EditText) findViewById(R.id.note_input_date);
        timeField = (EditText) findViewById(R.id.note_input_time);

        updateInputFields();
        updateType();

        nameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateType() {
        spinner.setSelection(type.ordinal());

        switch (type) {
            case DEADLINE:
                setTitle(R.string.deadline_activity_title);
                break;
            case NOTE:
                setTitle(R.string.note_activity_title);
                break;
            case EXAM:
                setTitle(R.string.exam_activity_title);
                break;
            case EVENT:
                setTitle(R.string.event_activity_title);
                break;
        }
    }

    public void selectDate(View view) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                if (calendar == null) {
                    calendar = Calendar.getInstance();
                }

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateInputFields();
            }
        };

        Calendar pickerCalendar = calendar;

        if (pickerCalendar == null) {
             pickerCalendar = Calendar.getInstance();
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, listener,
                pickerCalendar.get(Calendar.YEAR),
                pickerCalendar.get(Calendar.MONTH),
                pickerCalendar.get(Calendar.DAY_OF_MONTH));

        Date now = new Date();

        datePickerDialog.getDatePicker().setMinDate(now.getTime());

        datePickerDialog.show();
    }

    public void selectTime(View view) {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (calendar == null) {
                    calendar = Calendar.getInstance();
                }

                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                fullDay = false;

                updateInputFields();
            }
        };

        Calendar pickerCalendar = calendar;

        if (pickerCalendar == null) {
            pickerCalendar = Calendar.getInstance();
        }

        TimePickerDialog timePicker = new TimePickerDialog(this, listener,
                pickerCalendar.get(Calendar.HOUR_OF_DAY),
                pickerCalendar.get(Calendar.MINUTE),
                true);

        timePicker.setButton(DialogInterface.BUTTON_NEUTRAL, getResources()
                .getString(R.string.deadline_activity_dialog_time_all_day), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fullDay = true;
                updateInputFields();
            }
        });

        timePicker.show();
    }

    private void updateInputFields() {
        if (calendar != null) {
            String dateFormatted = DateUtil.formatDate(this, calendar.getTime(), DateUtil.DateFormat.DAY);
            dateField.setText(dateFormatted);
        } else {
            dateField.setText(null);
        }

        if (fullDay) {
            timeField.setText(R.string.deadline_activity_input_time_all_day);
        } else {
            String timeFormatted = DateUtil.getDigitalTime(calendar.getTime());
            timeField.setText(timeFormatted);
        }

        validateInput();
    }

    /**
     * Populates action bar with appropriate menu items.
     *
     * @param menu  Menu
     * @return      Boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_deadline, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        MenuItem save = menu.findItem(R.id.action_save);
        save.setEnabled(inputValid);
        return true;
    }

    private void validateInput() {
        boolean inputWasValid = inputValid;
        boolean nameValid = !nameField.getText().toString().trim().isEmpty();

        boolean dateValid = calendar != null;

        inputValid = nameValid && dateValid;

        if (inputWasValid != inputValid) {
            invalidateOptionsMenu();
        }
    }

    /**
     * Opens settings activity when corresponding menu option is selected.
     * @param item      Item that was selected
     * @return          Boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save:
                saveNote();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        if (fullDay) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
        }

        Note note = new Note();

        note.setName(nameField.getText().toString());
        note.setFullDay(fullDay);

        note.setDate(calendar.getTime());
        note.setNoteType(type);

        NoteStorage.addNote(this, note);
    }
}
