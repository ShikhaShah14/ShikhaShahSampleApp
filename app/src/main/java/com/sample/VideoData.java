package com.sample;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HTISPL on 22-Jun-18.
 */
public class VideoData extends Model {
    @Column(unique = true, onUniqueConflict = Column.ConflictAction.IGNORE)
    @SerializedName("id")
    private String videoId;

    @SerializedName("type")
    private String type;

    @SerializedName("title")
    private String title;

    @SerializedName("images")
    private ImageData imageData;

    @Column
    private int likeCounts;

    @Column
    private int dislikeCounts;

    public class ImageData {
        private long ImageDataId;

        @SerializedName("original_still")
        private ThumbnailData thumbnailData;

        @SerializedName("original")
        private VideoUrl videoUrl;

        public class ThumbnailData {
            @SerializedName("url")
            private String thumbnail;

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

        }

        public class VideoUrl {
            @SerializedName("mp4")
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

        }

        public ThumbnailData getThumbnailData() {
            return thumbnailData;
        }

        public void setThumbnailData(ThumbnailData thumbnailData) {
            this.thumbnailData = thumbnailData;
        }

        public VideoUrl getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(VideoUrl videoUrl) {
            this.videoUrl = videoUrl;
        }

        public long getImageDataId() {
            return ImageDataId;
        }

        public void setImageDataId(long imageDataId) {
            ImageDataId = imageDataId;
        }
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ImageData getImageData() {
        return imageData;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }

    public int getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(int likeCounts) {
        this.likeCounts = likeCounts;
    }

    public int getDislikeCounts() {
        return dislikeCounts;
    }

    public void setDislikeCounts(int dislikeCounts) {
        this.dislikeCounts = dislikeCounts;
    }

    public static VideoData getLikeDislikeCounts(String id) {
        return new Select().from(VideoData.class).where("videoId=?", id).executeSingle();
    }
}