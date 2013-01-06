package se.yumm.views;

import se.yumm.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActionBar extends RelativeLayout {


	private LayoutInflater m_inflater;

	private ImageView m_userBtn;
	private TextView m_title;
	private LinearLayout m_iconContainer;
	
	
	public ActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		m_inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		RelativeLayout barView = (RelativeLayout) m_inflater.inflate(R.layout.actionbar, null);
		addView(barView);

		m_userBtn = (ImageView) barView.findViewById(R.id.leftActionBarBtn);
		m_title = (TextView) barView.findViewById(R.id.actionbar_title);
		m_iconContainer = (LinearLayout) barView.findViewById(R.id.actionbar_actionIcons);
		
	}
	
	public void setHomeLogo(int resId) {
		setHomeLogo(resId, null);
	}

	public void setHomeLogo(int resId, OnClickListener onClickListener) {
		m_userBtn.setImageResource(resId);
		m_userBtn.setVisibility(View.VISIBLE);
		m_userBtn.setOnClickListener(onClickListener);
		if (onClickListener != null) {
		}
	}

	public void setTitle(CharSequence title) {
		m_title.setText(title);
	}

	public void setTitle(int resid) {
		m_title.setText(resid);
	}

	public void addActionIcon(int iconResourceId, OnClickListener onClickListener) {
		// Inflate
		View view = m_inflater.inflate(R.layout.actionbar_icon, m_iconContainer, false);
		ImageButton imgButton = (ImageButton) view.findViewById(R.id.actionbar_item);
		imgButton.setImageResource(iconResourceId);
		imgButton.setOnClickListener(onClickListener);

		m_iconContainer.addView(view, m_iconContainer.getChildCount());
	}


	public boolean removeActionIconAt(int index) {
		int count = m_iconContainer.getChildCount();
		if (count > 0 && index >= 0 && index < count) {
			m_iconContainer.removeViewAt(index);
			return true;
		}
		return false;
	}


}
