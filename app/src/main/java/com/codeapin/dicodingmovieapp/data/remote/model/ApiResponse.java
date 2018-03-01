package com.codeapin.dicodingmovieapp.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse implements Parcelable {

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<MovieItem> results;

	@SerializedName("total_results")
	private int totalResults;

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(List<MovieItem> results){
		this.results = results;
	}

	public List<MovieItem> getResults(){
		return results;
	}

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.page);
		dest.writeInt(this.totalPages);
		dest.writeTypedList(this.results);
		dest.writeInt(this.totalResults);
	}

	public ApiResponse() {
	}

	public ApiResponse(List<MovieItem> movieItemList) {
		setResults(movieItemList);
	}

	protected ApiResponse(Parcel in) {
		this.page = in.readInt();
		this.totalPages = in.readInt();
		this.results = in.createTypedArrayList(MovieItem.CREATOR);
		this.totalResults = in.readInt();
	}

	public static final Parcelable.Creator<ApiResponse> CREATOR = new Parcelable.Creator<ApiResponse>() {
		@Override
		public ApiResponse createFromParcel(Parcel source) {
			return new ApiResponse(source);
		}

		@Override
		public ApiResponse[] newArray(int size) {
			return new ApiResponse[size];
		}
	};
}