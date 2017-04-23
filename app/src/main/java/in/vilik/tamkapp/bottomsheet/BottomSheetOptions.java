package in.vilik.tamkapp.bottomsheet;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.menus.Campusravita;

/**
 * Created by vili on 23/04/2017.
 */

public class BottomSheetOptions extends BottomSheetDialog {
    private Context context;
    private LinearLayout rootView;

    public BottomSheetOptions(@NonNull Context context) {
        super(context);

        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);

        rootView = (LinearLayout) inflater.inflate(R.layout.bottom_sheet_dialog, null);

        setContentView(rootView);
    }

    public void setCategories(List<Category> categories) {
        LayoutInflater inflater = LayoutInflater.from(context);

        for (Category category : categories) {
            View categoryView = inflater.inflate(R.layout.bottom_sheet_dialog_category, null);

            TextView categoryText = (TextView) categoryView.findViewById(R.id.item_header);

            categoryText.setText(category.getName());

            rootView.addView(categoryView);

            for (Option option : category.getOptions()) {
                View optionView = inflater.inflate(R.layout.bottom_sheet_dialog_item, null);
                ImageView icon = (ImageView) optionView.findViewById(R.id.item_icon);

                icon.setImageDrawable(option.getIcon());

                TextView text = (TextView) optionView.findViewById(R.id.item_text);

                text.setText(option.getName());

                optionView.setOnClickListener(option.getListener());

                rootView.addView(optionView);
            }
        }
    }
}
