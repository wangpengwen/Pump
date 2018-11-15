package com.huxq17.download.listener;

import com.huxq17.download.DownloadInfo;
import com.huxq17.download.DownloadInfoSnapshot;

public abstract class DownloadObserver {
    private DownloadInfo.Status status;

    public DownloadObserver() {
    }


    /**
     * Filter the download information to be received, all received by default.
     *
     * @param downloadInfo The download info.
     * @return Receive if return true, or not receive.
     */
    public boolean filter(DownloadInfo downloadInfo) {
        return true;
    }

    private DownloadInfo downloadInfo;

    public abstract void onProgress(int progress);

    public DownloadInfo.Status getStatus() {
        return status;
    }

    public void onSuccess() {
    }

    public void onFailed() {
    }

    public DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    public final void downloading(DownloadInfoSnapshot downloadInfoSnapshot) {
        DownloadInfo downloadInfo = downloadInfoSnapshot.downloadInfo;
        long completedSize = downloadInfoSnapshot.completedSize;
        DownloadInfo.Status status = downloadInfoSnapshot.status;
        this.downloadInfo = downloadInfo;
        this.status = status;
        long contentLength = downloadInfo.getContentLength();
        int progress = contentLength == 0 ? 0 : (int) (completedSize * 1f / contentLength * 100);
        onProgress(progress);
        if (progress == 100) {
            onSuccess();
        } else if (status == DownloadInfo.Status.FAILED) {
            onFailed();
        }
    }
}