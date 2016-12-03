package cn.iterlog.xmimagepicker.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import cn.iterlog.xmimagepicker.Constants;
import cn.iterlog.xmimagepicker.Utils.MediaController;

public class MediasLogic {
    private static MediasLogic ourInstance = new MediasLogic();
    private String[] filterMimeTypes = null;
    private int mediaType = Constants.MEDIA_PICTURE;
    private int pictueAlbumIndex = 0;
    private int videoAlbumIndex = 0;
    private ArrayList<MediaController.AlbumEntry> pictureAlbums = new ArrayList<>();
    private ArrayList<MediaController.AlbumEntry> videoAlbums = new ArrayList<>();
    private HashMap<Integer, MediaController.PhotoEntry> selectedPhotos = new HashMap<>();
    private boolean loading = false;
    private HashMap<Object, MediaListener> listeners = new HashMap<>();
    public static MediasLogic getInstance() {
        return ourInstance;
    }

    private MediasLogic() {
    }

    public static MediasLogic getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(MediasLogic ourInstance) {
        MediasLogic.ourInstance = ourInstance;
    }

    public String[] getFilterMimeTypes() {
        return filterMimeTypes;
    }

    public void setFilterMimeTypes(String[] filterMimeTypes) {
        this.filterMimeTypes = filterMimeTypes;
    }

    public ArrayList<MediaController.AlbumEntry> getPictureAlbums() {
        return pictureAlbums;
    }

    public void setPictureAlbums(ArrayList<MediaController.AlbumEntry> pictureAlbums) {
        this.pictureAlbums = pictureAlbums;
        notify(Constants.MEDIA_PICTURE);
        notify(Constants.NOTIFY_TYPE_DIRECTORY);
    }

    public ArrayList<MediaController.AlbumEntry> getVideoAlbums() {
        return videoAlbums;
    }

    public void setVideoAlbums(ArrayList<MediaController.AlbumEntry> videoAlbums) {
        this.videoAlbums = videoAlbums;
        notify(Constants.MEDIA_MOVIE);
        notify(Constants.NOTIFY_TYPE_DIRECTORY);
    }

    public HashMap<Integer, MediaController.PhotoEntry> getSelectedPhotos() {
        return selectedPhotos;
    }

    public void setSelectedPhotos(HashMap<Integer, MediaController.PhotoEntry> selectedPhotos) {
        this.selectedPhotos = selectedPhotos;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public void registerListener(Object obj, MediaListener listener){
        listeners.put(obj, listener);
    }
    public void unRegisterListener(Object obj){
        listeners.remove(obj);
    }

    public List<MediaController.PhotoEntry> loadMedias(int mediaType) {
        if (mediaType == Constants.MEDIA_PICTURE && MediasLogic.getInstance().getPictureAlbums().size() > 0) {
            return MediasLogic.getInstance().getPictureAlbums().get(pictueAlbumIndex).photos;
        } else if (mediaType == Constants.MEDIA_MOVIE && MediasLogic.getInstance().getVideoAlbums().size() > 0) {
            return MediasLogic.getInstance().getVideoAlbums().get(videoAlbumIndex).photos;
        }
        return Collections.EMPTY_LIST;
    }

    public boolean isChooseAlbum(boolean isVideo, int position) {
        if(isVideo){
            return position == videoAlbumIndex;
        } else {
            return position == pictueAlbumIndex;
        }
    }

    public List<MediaController.AlbumEntry> getChooseAlbum() {
        if(mediaType == Constants.MEDIA_PICTURE){
            return pictureAlbums;
        } else if(mediaType == Constants.MEDIA_MOVIE){
            return videoAlbums;
        }
        return Collections.EMPTY_LIST;
    }

    public String getChooseAlbumName() {
        if(mediaType == Constants.MEDIA_PICTURE){
            if(pictureAlbums.size() > pictueAlbumIndex){
                return pictureAlbums.get(pictueAlbumIndex).bucketName;
            } else {
                return "所有图片";
            }
        } else if(mediaType == Constants.MEDIA_MOVIE){
            if(videoAlbums.size() > videoAlbumIndex){
                return videoAlbums.get(videoAlbumIndex).bucketName;
            } else {
                return "所有视频";
            }
        }
        return "";
    }

    public void setChooIndex(boolean isVideo, int position) {
        if(isVideo){
            videoAlbumIndex = position;
            notify(Constants.MEDIA_MOVIE);
        } else {
            pictueAlbumIndex = position;
            notify(Constants.MEDIA_PICTURE);
        }
    }

    public void clearData() {
        mediaType = Constants.MEDIA_PICTURE;
        pictueAlbumIndex = 0;
        videoAlbumIndex = 0;
    }

    public interface MediaListener {
        void onMediaLoaded(int type);
    }

    private void notify(int type){
        for(MediaListener listener:listeners.values()){
           listener.onMediaLoaded(type);
        }
    }

    public void updateMediaType(int mediaType){
        this.mediaType = mediaType;
        Log.i("MediasLogic", "updateMediaType mediaType:"+mediaType);
        notify(Constants.NOTIFY_TYPE_DIRECTORY);
    }
}
