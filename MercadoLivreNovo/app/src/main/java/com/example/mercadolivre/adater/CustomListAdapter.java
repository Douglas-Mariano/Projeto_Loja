package com.example.mercadolivre.adater;

import com.example.mercadolivre.R;
import com.example.mercadolivre.app.AppController;
import com.example.mercadolivre.model.Oferta;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
public class CustomListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Oferta> ofertaItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public CustomListAdapter(Activity activity, List<Oferta> ofertaItems) {
		this.activity = activity;
		this.ofertaItems = ofertaItems;
	}

	@Override
	public int getCount() {
		return ofertaItems.size();
	}

	@Override
	public Object getItem(int location) {
		return ofertaItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.thumbnail);
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView price = (TextView) convertView.findViewById(R.id.price);
		TextView condition = (TextView) convertView.findViewById(R.id.condition);
		TextView quant = (TextView) convertView.findViewById(R.id.releaseQuant);

		// getting movie data for the row
		Oferta m = ofertaItems.get(position);

		// thumbnail image
		thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
		
		// title
		title.setText(m.getTitle());
		
		// price
		price.setText("Price: " + String.valueOf(m.getPrice()));
		
		// condition
		String conditionStr = "";
		for (String str : m.getCondition()) {
			conditionStr += str + ", ";
		}
		conditionStr = conditionStr.length() > 0 ? conditionStr.substring(0,
				conditionStr.length() - 2) : conditionStr;
		condition.setText(conditionStr);
		
		// release quant
		quant.setText(String.valueOf(m.getQuant()));

		return convertView;
	}

}