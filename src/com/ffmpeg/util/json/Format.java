package com.ffmpeg.util.json;
public class Format {

    private String filename;
    private Integer nbStreams;
    private Integer nbPrograms;
    private String formatName;
    private String formatLongName;
    private String startTime;
    private String duration;
    private String size;
    private String bitRate;
    private Integer probeScore;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getNbStreams() {
        return nbStreams;
    }

    public void setNbStreams(Integer nbStreams) {
        this.nbStreams = nbStreams;
    }

    public Integer getNbPrograms() {
        return nbPrograms;
    }

    public void setNbPrograms(Integer nbPrograms) {
        this.nbPrograms = nbPrograms;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public String getFormatLongName() {
        return formatLongName;
    }

    public void setFormatLongName(String formatLongName) {
        this.formatLongName = formatLongName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public Integer getProbeScore() {
        return probeScore;
    }

    public void setProbeScore(Integer probeScore) {
        this.probeScore = probeScore;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Format.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("filename");
        sb.append('=');
        sb.append(((this.filename == null)?"<null>":this.filename));
        sb.append(',');
        sb.append("nbStreams");
        sb.append('=');
        sb.append(((this.nbStreams == null)?"<null>":this.nbStreams));
        sb.append(',');
        sb.append("nbPrograms");
        sb.append('=');
        sb.append(((this.nbPrograms == null)?"<null>":this.nbPrograms));
        sb.append(',');
        sb.append("formatName");
        sb.append('=');
        sb.append(((this.formatName == null)?"<null>":this.formatName));
        sb.append(',');
        sb.append("formatLongName");
        sb.append('=');
        sb.append(((this.formatLongName == null)?"<null>":this.formatLongName));
        sb.append(',');
        sb.append("startTime");
        sb.append('=');
        sb.append(((this.startTime == null)?"<null>":this.startTime));
        sb.append(',');
        sb.append("duration");
        sb.append('=');
        sb.append(((this.duration == null)?"<null>":this.duration));
        sb.append(',');
        sb.append("size");
        sb.append('=');
        sb.append(((this.size == null)?"<null>":this.size));
        sb.append(',');
        sb.append("bitRate");
        sb.append('=');
        sb.append(((this.bitRate == null)?"<null>":this.bitRate));
        sb.append(',');
        sb.append("probeScore");
        sb.append('=');
        sb.append(((this.probeScore == null)?"<null>":this.probeScore));
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
        result = ((result* 31)+((this.duration == null)? 0 :this.duration.hashCode()));
        result = ((result* 31)+((this.filename == null)? 0 :this.filename.hashCode()));
        result = ((result* 31)+((this.nbPrograms == null)? 0 :this.nbPrograms.hashCode()));
        result = ((result* 31)+((this.size == null)? 0 :this.size.hashCode()));
        result = ((result* 31)+((this.bitRate == null)? 0 :this.bitRate.hashCode()));
        result = ((result* 31)+((this.nbStreams == null)? 0 :this.nbStreams.hashCode()));
        result = ((result* 31)+((this.formatName == null)? 0 :this.formatName.hashCode()));
        result = ((result* 31)+((this.startTime == null)? 0 :this.startTime.hashCode()));
        result = ((result* 31)+((this.probeScore == null)? 0 :this.probeScore.hashCode()));
        result = ((result* 31)+((this.formatLongName == null)? 0 :this.formatLongName.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Format) == false) {
            return false;
        }
        Format rhs = ((Format) other);
        return (((((((((((this.duration == rhs.duration)||((this.duration!= null)&&this.duration.equals(rhs.duration)))&&((this.filename == rhs.filename)||((this.filename!= null)&&this.filename.equals(rhs.filename))))&&((this.nbPrograms == rhs.nbPrograms)||((this.nbPrograms!= null)&&this.nbPrograms.equals(rhs.nbPrograms))))&&((this.size == rhs.size)||((this.size!= null)&&this.size.equals(rhs.size))))&&((this.bitRate == rhs.bitRate)||((this.bitRate!= null)&&this.bitRate.equals(rhs.bitRate))))&&((this.nbStreams == rhs.nbStreams)||((this.nbStreams!= null)&&this.nbStreams.equals(rhs.nbStreams))))&&((this.formatName == rhs.formatName)||((this.formatName!= null)&&this.formatName.equals(rhs.formatName))))&&((this.startTime == rhs.startTime)||((this.startTime!= null)&&this.startTime.equals(rhs.startTime))))&&((this.probeScore == rhs.probeScore)||((this.probeScore!= null)&&this.probeScore.equals(rhs.probeScore))))&&((this.formatLongName == rhs.formatLongName)||((this.formatLongName!= null)&&this.formatLongName.equals(rhs.formatLongName))));
    }

}
