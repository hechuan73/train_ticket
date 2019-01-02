package ts.trainticket.fragement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import ts.trainticket.CityChooseActivity;
import ts.trainticket.TravelPathActivity;
import ts.trainticket.MainActivity;
import ts.trainticket.R;
import ts.trainticket.httpUtils.RxHttpUtils;
import ts.trainticket.httpUtils.UrlProperties;
import ts.trainticket.utils.ApplicationPreferences;
import ts.trainticket.utils.CalendarUtil;

public class Reserve_Fragment extends BaseFragment {

    // head
    private Button head_back_btn = null;
    private TextView headText;

    // city
    private int clickCity = -1;
    private CharSequence startCityName = null;
    private Button startCity = null;
    private CharSequence arriveCityName = null;
    private Button arriveCity = null;

    // Date
    private Calendar startDate = null;
    private Button startDateBtn = null;
    // start Time
    private Calendar startTime = null;
    private Button startTimeBtn = null;
    // end time
    private Calendar endTime = null;
    private Button endTimeBtn = null;


    private Button search_path_id = null;
    public static final String START_CITY = "start_city";
    public static final String END_CITY = "end_city";
    public static final String START_DATE = "pathStartDate";
    public static final String START_TIME = "start_time";
    public static final String ARRIVE_TIME = "arrive_time";

    public static final String CAR_TYPE_typeGD = "car_typegd";
    public static final String CAR_TYPE_typeZ = "car_typez";
    public static final String CAR_TYPE_typeT = "car_typet";
    public static final String CAR_TYPE_typeK = "car_typek";

    // train type
    private CheckBox typeAll = null;
    private CheckBox typeGD = null;
    private CheckBox typeZ = null;
    private CheckBox typeT = null;
    private CheckBox typeK = null;

    private LinearLayout memoryPathPanel = null;
    private static final String MEMORY_PATH_SPLITTER = " - ";
    private MemoryViewAdapter memoryViewAdapter = null;
    private List<Object> memoryPaths = null;
    RecyclerView memoryPathView = null;
    private List<Object> allCities = null;
    private List<Object> allStationTimes = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_reserve, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        initHead(view);
        initMemeoryPathButtons(view);
        initCityChooseButtons(view);
        initDateChooseButtons(view);
        initTimeChooseButton(view);

        initTrainTypeButton(view);
        initSearchButton(view);
        initCities();
    }

    @Override
    public void onStart() {
        super.onStart();
        String startCityv = startCity.getText().toString();
        String arriveCityV = arriveCity.getText().toString();
        if (startCityv == null || "".equals(startCityv))
            startCity.setText("Nan Jing");
        if (arriveCityV == null || "".equals(arriveCityV))
            arriveCity.setText("Shang Hai");
        showMemoryPath();
    }

    private void initHead(View view) {
        head_back_btn = (Button) view.findViewById(R.id.common_head_back_btn);
        head_back_btn.setVisibility(View.GONE);
        headText = (TextView) view.findViewById(R.id.title_head_tv);
        headText.setText("Ticket Reserve");
    }

    // init search button
    private void initSearchButton(View view) {
        search_path_id = (Button) view.findViewById(R.id.search_path_id);
        search_path_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), TravelPathActivity.class);
                intent.putExtra(Reserve_Fragment.START_CITY, startCity.getText());
                intent.putExtra(Reserve_Fragment.END_CITY, arriveCity.getText());
                intent.putExtra(Reserve_Fragment.START_DATE, CalendarUtil.formatMMddyyyy(startDateBtn.getText().toString()));

                intent.putExtra(Reserve_Fragment.START_TIME, startTimeBtn.getText());
                intent.putExtra(Reserve_Fragment.ARRIVE_TIME, endTimeBtn.getText());


                if (typeGD.isChecked())
                    intent.putExtra(Reserve_Fragment.CAR_TYPE_typeGD, "GD");
                else
                    intent.putExtra(Reserve_Fragment.CAR_TYPE_typeGD, "gd");

                if (typeZ.isChecked())
                    intent.putExtra(Reserve_Fragment.CAR_TYPE_typeZ, "Z");
                else
                    intent.putExtra(Reserve_Fragment.CAR_TYPE_typeZ, "z");

                if (typeT.isChecked())
                    intent.putExtra(Reserve_Fragment.CAR_TYPE_typeT, "T");
                else
                    intent.putExtra(Reserve_Fragment.CAR_TYPE_typeT, "t");

                if (typeK.isChecked())
                    intent.putExtra(Reserve_Fragment.CAR_TYPE_typeK, "K");
                else
                    intent.putExtra(Reserve_Fragment.CAR_TYPE_typeK, "k");


                String newPath2 = startCity.getText().toString() + MEMORY_PATH_SPLITTER + arriveCity.getText().toString();
                int ishave = 0;
                for (int i = 0; i < memoryPaths.size(); i++) {
                    if (memoryPaths.get(i).equals(newPath2))
                        ishave = 1;
                    if (memoryPaths.size() > 3)
                        memoryPaths.remove(0);
                }
                if (ishave == 0) {
                    memoryPaths.add(memoryPaths.size(), newPath2);
                    ApplicationPreferences.setMemoryPaths(getContext(), memoryPaths);
                }
                if (memoryPaths.size() > 3)
                    memoryPaths.remove(0);
                startActivity(intent);
            }
        });
    }

    private void initCities() {
        allCities = ApplicationPreferences.getAllCities(getActivity());
        allStationTimes = ApplicationPreferences.getStayTimeStation(getActivity());

        if (allCities.isEmpty()) {
            String listCityUri = UrlProperties.clientIstioIp + UrlProperties.getAllCity;
            subscription = RxHttpUtils.getWithOutHeader(listCityUri, getContext())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(String responseResult) {
                            if (responseResult != null)
                                Toast.makeText(getActivity(), "request data success", Toast.LENGTH_SHORT).show();
                            JSONArray jsonArray = JSON.parseArray(responseResult);
                            Iterator it = jsonArray.iterator();
                            int i = 0;
                            while (it.hasNext()) {
                                JSONObject cityObj = (JSONObject) it.next();
                                allCities.add(i, cityObj.getString("name"));
                                allStationTimes.add(i, cityObj.getString("id") + "_" + cityObj.getString("stayTime"));
                                ApplicationPreferences.setAllCities(getActivity(), allCities);
                                ApplicationPreferences.setStayTimeStation(getActivity(), allStationTimes);
                                i++;
                            }
                        }
                    });
        }
    }


    private void initMemeoryPathButtons(View view) {
        memoryPathPanel = (LinearLayout) view.findViewById(R.id.memory_path_panel);
        memoryPathView = (RecyclerView) view.findViewById(R.id.memory_path_view);

        memoryPaths = ApplicationPreferences.getMemoryPaths(getActivity());
        if (memoryPaths.isEmpty()) {
            // default
            String newPath1 = "Nan Jing" + MEMORY_PATH_SPLITTER + "Shang Hai";
            String newPath2 = "Shang Hai" + MEMORY_PATH_SPLITTER + "Nan Jing";
            memoryPaths.add(0, newPath1);
            memoryPaths.add(1, newPath2);
        } else if (null == startCityName || null == arriveCityName) {
            String[] citiesName = memoryPaths.get(0).toString().split(MEMORY_PATH_SPLITTER);
            startCityName = citiesName[0];
            arriveCityName = citiesName[1];
        }
        for (int i = 0; i < memoryPaths.size(); i++) {
            if (memoryPaths.size() > 3)
                memoryPaths.remove(0);
        }
    }

    public void showMemoryPath() {
        // init  RecycleView
        memoryViewAdapter = new MemoryViewAdapter();
        memoryPathView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        memoryPathView.setAdapter(memoryViewAdapter);
    }

    private void initCityChooseButtons(View view) {
        startCity = (Button) view.findViewById(R.id.btn_start_city);
        arriveCity = (Button) view.findViewById(R.id.btn_arrive_city);
        Button changeCity = (Button) view.findViewById(R.id.btn_change);

        addToBtnController(startCity, arriveCity, changeCity);
        if (null == startCityName) ;
        {
            startCityName = "Nan Jing";
        }
        if (null == arriveCityName) {
            arriveCityName = "Shang Hai";
        }
        changeShowCity();
        startCity.setOnClickListener(new CityChooseListener(R.id.btn_start_city));
        arriveCity.setOnClickListener(new CityChooseListener(R.id.btn_arrive_city));
        changeCity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                lockClick();
                switchCities();
                unlockClick();
            }
        });
    }

    private void initTrainTypeButton(View view) {
        typeAll = (CheckBox) view.findViewById(R.id.btn_type_all);
        typeGD = (CheckBox) view.findViewById(R.id.btn_type_gd);
        typeZ = (CheckBox) view.findViewById(R.id.btn_type_z);
        typeT = (CheckBox) view.findViewById(R.id.btn_type_t);
        typeK = (CheckBox) view.findViewById(R.id.btn_type_k);
        addToBtnController(typeAll, typeGD, typeZ, typeT, typeK);
        typeAll.setOnCheckedChangeListener(new TrainTypeChooseListener(true));
        typeGD.setOnCheckedChangeListener(new TrainTypeChooseListener(false));
        typeZ.setOnCheckedChangeListener(new TrainTypeChooseListener(false));
        typeT.setOnCheckedChangeListener(new TrainTypeChooseListener(false));
        typeK.setOnCheckedChangeListener(new TrainTypeChooseListener(false));
        changeShowAllTrainType();
    }


    private void initDateChooseButtons(View view) {
        startDateBtn = (Button) view.findViewById(R.id.btn_start_date);
        addToBtnController(startDateBtn);
        if (null == startDate) {
            startDate = Calendar.getInstance();
        }
        changeShowDate();
        startDateBtn.setOnClickListener(new DateChooseListener());
    }

    private void initTimeChooseButton(View view) {
        startTimeBtn = (Button) view.findViewById(R.id.btn_start_time);
        endTimeBtn = (Button) view.findViewById(R.id.btn_end_time);
        addToBtnController(startTimeBtn, endTimeBtn);

        if (null == startTime || null == endTime) {
            startTime = Calendar.getInstance();
            setDateTime(startTime, 0, 0, 0, 0);
            endTime = Calendar.getInstance();
            setDateTime(endTime, 23, 59, 59, 999);
        }
        changeShowTime(R.id.btn_start_time);
        startTimeBtn.setOnClickListener(new TimeChooseListener(startTimeBtn.getId(), startTime));
        endTimeBtn.setOnClickListener(new TimeChooseListener(endTimeBtn.getId(), endTime));
    }


    private void changeShowCity() {
        if (startCityName.equals(arriveCityName)) {
            switchCities();
        } else {
            startCity.setText(startCityName);
            arriveCity.setText(arriveCityName);
        }
    }

    public void switchCities() {
        CharSequence preStartCityName = startCity.getText();
        startCity.setText(arriveCity.getText());
        arriveCity.setText(preStartCityName);

        startCityName = startCity.getText();
        arriveCityName = arriveCity.getText();
    }


    private void setDateTime(Calendar time, int hour, int minute, int second, int millisecond) {
        time.set(Calendar.HOUR_OF_DAY, hour);
        time.set(Calendar.MINUTE, minute);
        time.set(Calendar.SECOND, second);
        time.set(Calendar.MILLISECOND, millisecond);
    }

    // change date
    private void changeShowDate() {
        String dateStr = SimpleDateFormat.getDateInstance().format(startDate.getTime());
        // Dec 19, 2018
        startDateBtn.setText(dateStr);
    }

    // change time
    private void changeShowTime(int viewId) {
        if (startTime.after(endTime)) {
            if (viewId == R.id.btn_start_time) {
                endTime.setTime(startTime.getTime());
            } else if (viewId == R.id.btn_end_time) {
                startTime.setTime(endTime.getTime());
            }
        }
        String startTimeStr = CalendarUtil.getDayFormatStr(startTime);
        startTimeBtn.setText(startTimeStr);
        String endTimeStr = CalendarUtil.getDayFormatStr(endTime);
        endTimeBtn.setText(endTimeStr);
    }

    private boolean isAllTypesChecked() {
        return typeGD.isChecked() && typeZ.isChecked() && typeT.isChecked() && typeK.isChecked();
    }

    private void changeShowAllTrainType() {
        typeAll.setChecked(true);
        typeGD.setChecked(false);
        typeZ.setChecked(false);
        typeT.setChecked(false);
        typeK.setChecked(false);
    }

    private void changeShowPartTrainTypes() {
        typeAll.setChecked(false);
    }

    private boolean isAllTypesUnChecked() {
        return !typeGD.isChecked() && !typeZ.isChecked() && !typeT.isChecked() && !typeK.isChecked();
    }

    private class CityChooseListener implements View.OnClickListener {

        int city;

        public CityChooseListener(int city) {
            this.city = city;
        }

        @Override
        public void onClick(View v) {
            Intent cityIntent = new Intent(getActivity(), CityChooseActivity.class);
            startActivityForResult(cityIntent, CityChooseActivity.CITY_CHOOSE_REQUEST_CODE);
            clickCity = city;
        }
    }

    private class TrainTypeChooseListener implements CheckBox.OnCheckedChangeListener {
        private boolean isTypeAll;

        public TrainTypeChooseListener(boolean isTypeAll) {
            this.isTypeAll = isTypeAll;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!buttonView.isPressed()) {
                return;
            }
            if (isChecked) {
                if (isTypeAll || isAllTypesChecked()) {
                    changeShowAllTrainType();
                } else {
                    changeShowPartTrainTypes();
                }
            } else {
                if (isTypeAll || isAllTypesUnChecked()) {
                    changeShowAllTrainType();
                } else {
                    changeShowPartTrainTypes();
                }
            }
        }
    }

    private class MemoryViewAdapter extends RecyclerView.Adapter<MemoryViewAdapter.MemoryPathsHolder> {

        @Override
        public MemoryPathsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View memoryBtn = LayoutInflater.from(getActivity()).inflate(R.layout.item_button_memory_path, parent, false);
            return new MemoryPathsHolder(memoryBtn);
        }

        @Override
        public void onBindViewHolder(MemoryPathsHolder holder, int position) {
            holder.memoryBtn.setText(memoryPaths.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return memoryPaths.size();
        }

        public class MemoryPathsHolder extends RecyclerView.ViewHolder {
            Button memoryBtn;

            public MemoryPathsHolder(View itemView) {
                super(itemView);
                memoryBtn = (Button) itemView.findViewById(R.id.btn_memory_path);
                addToBtnController(memoryBtn);
                memoryBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        lockClick();
                        String[] cities = ((Button) v).getText().toString().split(MEMORY_PATH_SPLITTER);
                        startCityName = cities[0];
                        arriveCityName = cities[1];
                        changeShowCity();
                        unlockClick();
                    }
                });
            }
        }
    }

    private class DateChooseListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            lockClick();
            Calendar now = CalendarUtil.getToday();

            DatePickerDialog datePicker = DatePickerDialog.newInstance(
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
                            startDate.set(year, monthOfYear, dayOfMonth);
                            changeShowDate();
                        }
                    },
                    startDate.get(Calendar.YEAR),
                    startDate.get(Calendar.MONTH),
                    startDate.get(Calendar.DAY_OF_MONTH)
            );

            Calendar lastDay = CalendarUtil.getLastDay();
            datePicker.setMinDate(now);
            datePicker.setMaxDate(lastDay);

            datePicker.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    unlockClick();
                }
            });

            datePicker.show(getActivity().getFragmentManager(), "" + startDateBtn.getId());
        }
    }

    public class TimeChooseListener implements View.OnClickListener {

        private Calendar initTime;
        private int id;

        public TimeChooseListener(int id, Calendar initTime) {
            this.id = id;
            this.initTime = initTime;
        }

        @Override
        public void onClick(View v) {
            lockClick();
            TimePickerDialog timePicker = TimePickerDialog.newInstance(
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                            initTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            initTime.set(Calendar.MINUTE, minute);
                            initTime.set(Calendar.SECOND, second);
                            changeShowTime(id);
                        }
                    },
                    initTime.get(Calendar.HOUR_OF_DAY),
                    initTime.get(Calendar.MINUTE),
                    true
            );
            timePicker.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    unlockClick();
                }
            });
            timePicker.show(getActivity().getFragmentManager(), "" + id);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (MainActivity.CITY_CHOOSE_RESULT == resultCode && CityChooseActivity.CITY_CHOOSE_REQUEST_CODE == requestCode) {
            unlockClick();
            if (null == data) {
                return;
            }
            if (R.id.btn_start_city == clickCity) {
                startCityName = data.getStringExtra(CityChoose_Fragement.CITY_CHOOSED);
            } else if (R.id.btn_arrive_city == clickCity) {
                arriveCityName = data.getStringExtra(CityChoose_Fragement.CITY_CHOOSED);
            }
            changeShowCity();
        }
    }
}
