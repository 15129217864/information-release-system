package com.ffmpeg.util.json;
public class Disposition {

    private Integer _default;
    private Integer dub;
    private Integer original;
    private Integer comment;
    private Integer lyrics;
    private Integer karaoke;
    private Integer forced;
    private Integer hearingImpaired;
    private Integer visualImpaired;
    private Integer cleanEffects;
    private Integer attachedPic;
    private Integer timedThumbnails;

    public Integer getDefault() {
        return _default;
    }

    public void setDefault(Integer _default) {
        this._default = _default;
    }

    public Integer getDub() {
        return dub;
    }

    public void setDub(Integer dub) {
        this.dub = dub;
    }

    public Integer getOriginal() {
        return original;
    }

    public void setOriginal(Integer original) {
        this.original = original;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getLyrics() {
        return lyrics;
    }

    public void setLyrics(Integer lyrics) {
        this.lyrics = lyrics;
    }

    public Integer getKaraoke() {
        return karaoke;
    }

    public void setKaraoke(Integer karaoke) {
        this.karaoke = karaoke;
    }

    public Integer getForced() {
        return forced;
    }

    public void setForced(Integer forced) {
        this.forced = forced;
    }

    public Integer getHearingImpaired() {
        return hearingImpaired;
    }

    public void setHearingImpaired(Integer hearingImpaired) {
        this.hearingImpaired = hearingImpaired;
    }

    public Integer getVisualImpaired() {
        return visualImpaired;
    }

    public void setVisualImpaired(Integer visualImpaired) {
        this.visualImpaired = visualImpaired;
    }

    public Integer getCleanEffects() {
        return cleanEffects;
    }

    public void setCleanEffects(Integer cleanEffects) {
        this.cleanEffects = cleanEffects;
    }

    public Integer getAttachedPic() {
        return attachedPic;
    }

    public void setAttachedPic(Integer attachedPic) {
        this.attachedPic = attachedPic;
    }

    public Integer getTimedThumbnails() {
        return timedThumbnails;
    }

    public void setTimedThumbnails(Integer timedThumbnails) {
        this.timedThumbnails = timedThumbnails;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Disposition.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("_default");
        sb.append('=');
        sb.append(((this._default == null)?"<null>":this._default));
        sb.append(',');
        sb.append("dub");
        sb.append('=');
        sb.append(((this.dub == null)?"<null>":this.dub));
        sb.append(',');
        sb.append("original");
        sb.append('=');
        sb.append(((this.original == null)?"<null>":this.original));
        sb.append(',');
        sb.append("comment");
        sb.append('=');
        sb.append(((this.comment == null)?"<null>":this.comment));
        sb.append(',');
        sb.append("lyrics");
        sb.append('=');
        sb.append(((this.lyrics == null)?"<null>":this.lyrics));
        sb.append(',');
        sb.append("karaoke");
        sb.append('=');
        sb.append(((this.karaoke == null)?"<null>":this.karaoke));
        sb.append(',');
        sb.append("forced");
        sb.append('=');
        sb.append(((this.forced == null)?"<null>":this.forced));
        sb.append(',');
        sb.append("hearingImpaired");
        sb.append('=');
        sb.append(((this.hearingImpaired == null)?"<null>":this.hearingImpaired));
        sb.append(',');
        sb.append("visualImpaired");
        sb.append('=');
        sb.append(((this.visualImpaired == null)?"<null>":this.visualImpaired));
        sb.append(',');
        sb.append("cleanEffects");
        sb.append('=');
        sb.append(((this.cleanEffects == null)?"<null>":this.cleanEffects));
        sb.append(',');
        sb.append("attachedPic");
        sb.append('=');
        sb.append(((this.attachedPic == null)?"<null>":this.attachedPic));
        sb.append(',');
        sb.append("timedThumbnails");
        sb.append('=');
        sb.append(((this.timedThumbnails == null)?"<null>":this.timedThumbnails));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this._default == null)? 0 :this._default.hashCode()));
        result = ((result* 31)+((this.original == null)? 0 :this.original.hashCode()));
        result = ((result* 31)+((this.attachedPic == null)? 0 :this.attachedPic.hashCode()));
        result = ((result* 31)+((this.forced == null)? 0 :this.forced.hashCode()));
        result = ((result* 31)+((this.dub == null)? 0 :this.dub.hashCode()));
        result = ((result* 31)+((this.karaoke == null)? 0 :this.karaoke.hashCode()));
        result = ((result* 31)+((this.cleanEffects == null)? 0 :this.cleanEffects.hashCode()));
        result = ((result* 31)+((this.timedThumbnails == null)? 0 :this.timedThumbnails.hashCode()));
        result = ((result* 31)+((this.comment == null)? 0 :this.comment.hashCode()));
        result = ((result* 31)+((this.hearingImpaired == null)? 0 :this.hearingImpaired.hashCode()));
        result = ((result* 31)+((this.lyrics == null)? 0 :this.lyrics.hashCode()));
        result = ((result* 31)+((this.visualImpaired == null)? 0 :this.visualImpaired.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Disposition) == false) {
            return false;
        }
        Disposition rhs = ((Disposition) other);
        return (((((((((((((this._default == rhs._default)||((this._default!= null)&&this._default.equals(rhs._default)))&&((this.original == rhs.original)||((this.original!= null)&&this.original.equals(rhs.original))))&&((this.attachedPic == rhs.attachedPic)||((this.attachedPic!= null)&&this.attachedPic.equals(rhs.attachedPic))))&&((this.forced == rhs.forced)||((this.forced!= null)&&this.forced.equals(rhs.forced))))&&((this.dub == rhs.dub)||((this.dub!= null)&&this.dub.equals(rhs.dub))))&&((this.karaoke == rhs.karaoke)||((this.karaoke!= null)&&this.karaoke.equals(rhs.karaoke))))&&((this.cleanEffects == rhs.cleanEffects)||((this.cleanEffects!= null)&&this.cleanEffects.equals(rhs.cleanEffects))))&&((this.timedThumbnails == rhs.timedThumbnails)||((this.timedThumbnails!= null)&&this.timedThumbnails.equals(rhs.timedThumbnails))))&&((this.comment == rhs.comment)||((this.comment!= null)&&this.comment.equals(rhs.comment))))&&((this.hearingImpaired == rhs.hearingImpaired)||((this.hearingImpaired!= null)&&this.hearingImpaired.equals(rhs.hearingImpaired))))&&((this.lyrics == rhs.lyrics)||((this.lyrics!= null)&&this.lyrics.equals(rhs.lyrics))))&&((this.visualImpaired == rhs.visualImpaired)||((this.visualImpaired!= null)&&this.visualImpaired.equals(rhs.visualImpaired))));
    }

}
