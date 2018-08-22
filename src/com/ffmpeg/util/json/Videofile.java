package com.ffmpeg.util.json;
import java.util.ArrayList;
import java.util.List;

public class Videofile {

    private List<Stream> streams = new ArrayList<Stream>();
    private Format format;

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Videofile.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("streams");
        sb.append('=');
        sb.append(((this.streams == null)?"<null>":this.streams));
        sb.append(',');
        sb.append("format");
        sb.append('=');
        sb.append(((this.format == null)?"<null>":this.format));
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
        result = ((result* 31)+((this.format == null)? 0 :this.format.hashCode()));
        result = ((result* 31)+((this.streams == null)? 0 :this.streams.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Videofile) == false) {
            return false;
        }
        Videofile rhs = ((Videofile) other);
        return (((this.format == rhs.format)||((this.format!= null)&&this.format.equals(rhs.format)))&&((this.streams == rhs.streams)||((this.streams!= null)&&this.streams.equals(rhs.streams))));
    }

}
