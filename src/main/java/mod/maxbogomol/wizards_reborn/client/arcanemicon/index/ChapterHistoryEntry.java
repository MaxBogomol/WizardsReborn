package mod.maxbogomol.wizards_reborn.client.arcanemicon.index;

import mod.maxbogomol.wizards_reborn.client.arcanemicon.Chapter;

public class ChapterHistoryEntry {
    public Chapter chapter;
    public int page;

    public ChapterHistoryEntry(Chapter chapter, int page) {
        this.chapter = chapter;
        this.page = page;
    }
}
