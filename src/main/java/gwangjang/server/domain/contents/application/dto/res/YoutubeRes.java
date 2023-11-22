package gwangjang.server.domain.contents.application.dto.res;


import gwangjang.server.domain.contents.domain.entity.Contents;

import java.util.List;

public class YoutubeRes {

    private List<Contents> items;

    public List<Contents> getItems() {
        return items;
    }

    public void setItems(List<Contents> items) {
        this.items = items;
    }
}

