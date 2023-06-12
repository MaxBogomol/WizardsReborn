package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.google.common.collect.Lists;
import java.util.List;

public class Chapter {
    public String titleKey;
    public List<Page> pages;

    public Chapter(String titleKey, Page... pages) {
        this.titleKey = titleKey;
        this.pages = Lists.newArrayList(pages);
    }

    public Page getPage(int i) {
        if (i >= size()) return null;
        return pages.get(i);
    }

    public int size() {
        return pages.size();
    }

    public void setPage(int i, Page page) {
        pages.set(i, page);
    }

    public void addPage(int i, Page page) {
        pages.add(i, page);
    }

    public void addPage(Page page) {
        pages.add(page);
    }

    public void removePage(Page page) {
        pages.remove(page);
    }

    public void removePage(int i) {
        pages.remove(i);
    }
}