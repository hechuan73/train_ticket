package ts.trainticket.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;


import java.util.List;

import ts.trainticket.MainActivity;
import ts.trainticket.R;
import ts.trainticket.utils.ApplicationPreferences;


public class CityChoose_Fragement extends BaseFragment {

    public static final String CITY_CHOOSED = "choosed_city";
    //searchView
    private String[] cities;
    List<Object> allCities = null;
    private SearchView city_searchView = null;
    private ListView city_listView = null;

    // city_help_panel
    LinearLayout city_help_panel = null;
    private List<Object> memoryCities = null;
    private MemoryViewAdapter memoryViewAdapter;

    //  hot city
    private String[] hot_cities = {"Shang Hai", "Shang Hai Hong Qiao", "Tai Yuan", "Bei Jing",
            "Nan Jing", "Shi Jia Zhuang", "Xu Zhou", "Ji Nan",
            "Hang Zhou", "Su Zhou", "Zhen Jiang", "Wu Xi"};
    private HotCityViewAdapter hotCityViewAdapter = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_city_choose, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        initCities();
        initMemoryCities(view);
        initHotCities(view);
        initSearchViews(view);
    }

    private void initCities(){
        allCities = ApplicationPreferences.getAllCities(getActivity());
        String []temps = new String[allCities.size()];
        for(int i = 0 ;i < allCities.size();i++){
            temps[i] = String.valueOf(allCities.get(i));
        }
        cities = temps;
    }

    private void initSearchViews(View view) {
        city_help_panel = (LinearLayout) view.findViewById(R.id.city_help_panel);
        city_searchView = (SearchView) view.findViewById(R.id.searchView);
        city_listView = (ListView) view.findViewById(R.id.search_city_listView);

        city_listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cities));

        city_listView.setTextFilterEnabled(true);
        city_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String map = (String) city_listView.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra(CityChoose_Fragement.CITY_CHOOSED, map);
               if(!memoryCities.contains(map)) // 没有才加进去
                memoryCities.add(map);
                if(memoryCities.size()>4)
                    memoryCities.remove(0);

                ApplicationPreferences.setMemoryCities(getContext(),memoryCities);
                getActivity().setResult(MainActivity.CITY_CHOOSE_RESULT, intent);
                getActivity().finish();
            }
        });

        city_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                ListAdapter adapter = city_listView.getAdapter();
                if (adapter instanceof Filterable) {
                    Filter filter = ((Filterable) adapter).getFilter();
                    if (!TextUtils.isEmpty(newText)) {
                        city_listView.setVisibility(View.VISIBLE);
                        city_help_panel.setVisibility(View.GONE);
                        filter.filter(newText);
                    } else {
                        filter.filter(null);
                        city_listView.setVisibility(View.GONE);
                        city_help_panel.setVisibility(View.VISIBLE);
                    }
                }
                return true;
            }
        });
    }

    private void initHotCities(View view) {
        RecyclerView hotCityView = (RecyclerView) view.findViewById(R.id.hot_city_view);
        hotCityViewAdapter = new HotCityViewAdapter();
        hotCityView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        hotCityView.setAdapter(hotCityViewAdapter);
    }

    private void initMemoryCities(View view) {
        RecyclerView memoryCityView = (RecyclerView) view.findViewById(R.id.memory_city_view);
        memoryCities = ApplicationPreferences.getMemoryCitiess(getActivity());

        memoryViewAdapter = new MemoryViewAdapter();
        memoryCityView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        memoryCityView.setAdapter(memoryViewAdapter);

    }

    private class MemoryViewAdapter extends RecyclerView.Adapter<MemoryViewAdapter.MemoryCitysHolder> {
        @Override
        public MemoryCitysHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View memoryBtn = LayoutInflater.from(getActivity()).inflate(
                    R.layout.item_button_memory_city, parent, false);
            return new MemoryCitysHolder(memoryBtn);
        }

        @Override
        public void onBindViewHolder(MemoryCitysHolder holder, int position) {
            holder.memoryBtn.setText(memoryCities.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return memoryCities.size();
        }

        public class MemoryCitysHolder extends RecyclerView.ViewHolder {

            Button memoryBtn;

            public MemoryCitysHolder(View itemView) {
                super(itemView);
                memoryBtn = (Button) itemView.findViewById(R.id.btn_memory_city);
                addToBtnController(memoryBtn);
                memoryBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra(CityChoose_Fragement.CITY_CHOOSED, memoryBtn.getText());
                        getActivity().setResult(MainActivity.CITY_CHOOSE_RESULT, intent);
                        getActivity().finish();
                    }
                });
            }
        }
    }

    private class HotCityViewAdapter extends RecyclerView.Adapter<HotCityViewAdapter.HotCityHolder> {

        @Override
        public HotCityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View memoryBtn = LayoutInflater.from(getActivity()).inflate(
                    R.layout.item_button_memory_city, parent, false);
            return new HotCityHolder(memoryBtn);
        }

        @Override
        public void onBindViewHolder(HotCityHolder holder, int position) {
            holder.hotCityBtn.setText(hot_cities[position]);
        }

        @Override
        public int getItemCount() {
            return hot_cities.length;
        }

        public class HotCityHolder extends RecyclerView.ViewHolder {
            Button hotCityBtn;

            public HotCityHolder(View itemView) {
                super(itemView);
                hotCityBtn = (Button) itemView.findViewById(R.id.btn_memory_city);
                addToBtnController(hotCityBtn);
                hotCityBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        lockClick();
                        Intent intent = new Intent();
                        intent.putExtra(CityChoose_Fragement.CITY_CHOOSED, hotCityBtn.getText());
                        getActivity().setResult(MainActivity.CITY_CHOOSE_RESULT, intent);
                        getActivity().finish();
                        unlockClick();
                    }
                });
            }
        }
    }
}
