package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnCheckedListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnViewClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Address;

/**
 * Created by Administrator on 2017/2/23.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHoader> {
    List<Address> mList;
    Context context;
    OnViewClickListener onViewClickListener;
    OnCheckedListener onCheckedListener;

    public void setOnCheckedListener(OnCheckedListener onCheckedListener) {
        this.onCheckedListener = onCheckedListener;
    }

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    public AddressAdapter(List<Address> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public ViewHoader onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false);
        return new ViewHoader(view);
    }

    @Override
    public void onBindViewHolder(ViewHoader holder, final int position) {
        Address address = mList.get(position);
        holder.address_phone_tv.setText(address.getPhone());
        holder.address_username_tv.setText(address.getName());
        String city = address.getSheng();
        if ("北京".equals(city) || "上海".equals(city) || "天津".equals(city) || "重庆".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
            holder.details_address_tv.setText(address.getShi() + address.getQu() + address.getAddress());
        } else {
            holder.details_address_tv.setText(city + address.getShi() + address.getQu() + address.getAddress());
        }


        holder.address_default_tv.setChecked(address.isDefa());

        if (address.isDefa()) {
            holder.address_default_tv.setEnabled(false);
        } else {
            holder.address_default_tv.setEnabled(true);
        }

        holder.address_edit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onViewClickListener != null)
                    onViewClickListener.OnViewClick(v, position, 0);
            }
        });
        holder.address_delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onViewClickListener != null)
                    onViewClickListener.OnViewClick(v, position, 1);
            }
        });

        holder.address_default_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCheckedListener != null)
                    onCheckedListener.onViewChecked(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHoader extends RecyclerView.ViewHolder {


        private CheckBox address_default_tv;
        private TextView address_username_tv, address_phone_tv, details_address_tv, address_edit_tv, address_delete_tv;


        public ViewHoader(View itemView) {
            super(itemView);

            address_default_tv = (CheckBox) itemView.findViewById(R.id.address_default_tv);
            address_username_tv = (TextView) itemView.findViewById(R.id.address_username_tv);
            address_phone_tv = (TextView) itemView.findViewById(R.id.address_phone_tv);
            details_address_tv = (TextView) itemView.findViewById(R.id.details_address_tv);
            address_edit_tv = (TextView) itemView.findViewById(R.id.address_edit_tv);
            address_delete_tv = (TextView) itemView.findViewById(R.id.address_delete_tv);
        }
    }

    public void setmList(List<Address> list) {

        this.mList = list;
        this.notifyDataSetChanged();
    }
}
