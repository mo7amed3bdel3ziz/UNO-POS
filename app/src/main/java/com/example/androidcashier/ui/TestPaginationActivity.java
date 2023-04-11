package com.example.androidcashier.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.androidcashier.R;
import com.example.androidcashier.databinding.ActivityTestPaginationBinding;
import com.example.androidcashier.network.RetrofItItems;

public class TestPaginationActivity extends AppCompatActivity {


    ActivityTestPaginationBinding binding;
    public static final  int cunt=10;
    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 3; //your total page
    private int currentPage = PAGE_START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pagination);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_pagination);


      //  RetrofItItems.getInstance().getApiCalls().getItemsPagnation(cunt,)


 // binding.rec.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
 //          @Override
 //          protected void loadMoreItems() {
 //              isLoading = true;
 //              currentPage += 1;
 //              loadApi(currentPage); //pass page number as parameter in your api calls
 //          }

 //          @Override
 //          public int getTotalPageCount() {
 //              return TOTAL_PAGES;
 //          }

 //          @Override
 //          public boolean isLastPage() {
 //              return isLastPage;
 //          }

 //          @Override
 //          public boolean isLoading() {
 //              return isLoading;
 //          }
 //      });


  }
}