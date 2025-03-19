package com.github.svyaz.airlinersbot.app.service.picture;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.datastore.service.PictureStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopPictureRequestServiceBean implements TopPictureRequestService {

    private final PictureStorageService pictureStorageService;

    @Override
    public Picture get() {
        return pictureStorageService.getTop()
                .orElse(null);
    }
}
