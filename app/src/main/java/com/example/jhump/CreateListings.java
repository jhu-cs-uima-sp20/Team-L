package com.example.jhump;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class CreateListings extends Fragment implements View.OnClickListener{
    private Button post;
    private Button cancel;
    private List<Bitmap> pics;
    private ImageButton gallery;
    private Spinner category;
    private Spinner condition;
    private EditText listingName;
    private EditText price;
    private EditText description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_create_listings, container, false);
        post = root.findViewById(R.id.post);
        cancel = root.findViewById(R.id.cancel);

        condition = root.findViewById(R.id.condition);
        category = root.findViewById(R.id.category);
        ArrayAdapter<CharSequence> catAdapt = ArrayAdapter.createFromResource(getContext(), R.array.cat_spinner ,android.R.layout.simple_spinner_item);
        catAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(catAdapt);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                String text = parent.getItemAtPosition(i).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Select Category", Toast.LENGTH_LONG).show();
            }
        });
        ArrayAdapter<CharSequence> conAdapt = ArrayAdapter.createFromResource(getContext(), R.array.con_spinner ,android.R.layout.simple_spinner_item);
        conAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        condition.setAdapter(conAdapt);
        condition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                String text = parent.getItemAtPosition(i).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Select Condition", Toast.LENGTH_LONG).show();
            }
        });
        post.setOnClickListener(this);
        cancel.setOnClickListener(this);
//        final String[] categoryItem = new String[1];
//        final String[] conditionItem = new String[1];
//        ArrayList<String> cat = new ArrayList<>();
//        cat.add("Textbook");
//        cat.add("I-Clicker");
//        cat.add("Lab Equipment");
//        ArrayList<String> con = new ArrayList<>();
//        con.add("Gently Used");
//        con.add("New");
//        con.add("Used");
//        con.add("Damaged");

//        ArrayAdapter<String> conAdapt = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, con);
//        condition.setAdapter(conAdapt);
//        category.setAdapter(catAdapt);
        return root;
    }

    public boolean checkAllInput(EditText listingName, ImageButton gallery, Spinner condition,
                                 Spinner category, EditText description, EditText price) {
        String listing = listingName.getText().toString();
        //gallery idk how to do?
        //Spinner
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post:
                //seller is hardcoded for now!
                listingName = view.findViewById(R.id.listing);
                gallery = view.findViewById(R.id.pic);
                condition = view.findViewById(R.id.condition);
                category = view.findViewById(R.id.category);
                description = view.findViewById(R.id.description);
                price = view.findViewById(R.id.price);
                if (!checkAllInput(listingName, gallery, condition, category, description, price)) {
                    CharSequence text = "Please complete all required fields.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getActivity(), text, duration);
                    toast.show();
                    return;
                }
                Item newItem = new Item(listingName.getText().toString(), pics,"John Doe",
                        "new" , "textbook", description.getText().toString(),
                        Double.parseDouble(price.getText().toString()), false );

            //case R.id.cancel:
                //where should this go to? like maybe
        }
    }
//
//        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                categoryItem[0] = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                categoryItem[0] = "Textbook";
//                //or throw TOAST?
//            }
//        });
//
//        condition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                conditionItem[0] = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                categoryItem[0] = "Used";
//                //or throw TOAST?
//            }
//        });
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

//        gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //below is to get pics from gallery
//                //was previously createlistings.this before class was made into a frag <---for Chioma
//                Context context = getApplicationContext();
//                if (ActivityCompat.checkSelfPermission(context,
//                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(CreateListings.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
//                    return;
//                }
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setType("image/*");
//                startActivityForResult(intent, 1);
//            }
//        });

//


    //should launch camera later on
    //for launching user gallery to select multiple images
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == RESULT_OK && requestCode == 1) {
//            pics = new ArrayList<>();
//            assert data != null;
//            ClipData clipData = data.getClipData();
//            if (clipData != null) {
//                for (int i = 0; i < clipData.getItemCount(); i++) {
//                    Uri imageURI = clipData.getItemAt(i).getUri();
//                    try {
//                        InputStream is = this.getContentResolver().openInputStream(imageURI);
//                        Bitmap bitmap = BitmapFactory.decodeStream(is);
//                        pics.add(bitmap);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } else {
//                Uri imageURI = data.getData();
//                try {
//                    assert imageURI != null;
//                    InputStream is = this.getContentResolver().openInputStream(imageURI);
//                    Bitmap bitmap = BitmapFactory.decodeStream(is);
//                    pics.add(bitmap);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
