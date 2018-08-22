package com.ffmpeg.util.json;
public class Stream {

    private Integer index;
    private String codecName;
    private String codecLongName;
    private String profile;
    private String codecType;
    private String codecTimeBase;
    private String codecTagString;
    private String codecTag;
    private Integer width;
    private Integer height;
    private Integer codedWidth;
    private Integer codedHeight;
    private Integer hasBFrames;
    private String sampleAspectRatio;
    private String displayAspectRatio;
    private String pixFmt;
    private Integer level;
    private String colorRange;
    private String chromaLocation;
    private String fieldOrder;
    private Integer refs;
    private String id;
    private String rFrameRate;
    private String avgFrameRate;
    private String timeBase;
    private Integer startPts;
    private String startTime;
    private String bitRate;
    private Disposition disposition;
    private String sampleFmt;
    private String sampleRate;
    private Integer channels;
    private String channelLayout;
    private Integer bitsPerSample;
    private Integer durationTs;
    private String duration;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getCodecName() {
        return codecName;
    }

    public void setCodecName(String codecName) {
        this.codecName = codecName;
    }

    public String getCodecLongName() {
        return codecLongName;
    }

    public void setCodecLongName(String codecLongName) {
        this.codecLongName = codecLongName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCodecType() {
        return codecType;
    }

    public void setCodecType(String codecType) {
        this.codecType = codecType;
    }

    public String getCodecTimeBase() {
        return codecTimeBase;
    }

    public void setCodecTimeBase(String codecTimeBase) {
        this.codecTimeBase = codecTimeBase;
    }

    public String getCodecTagString() {
        return codecTagString;
    }

    public void setCodecTagString(String codecTagString) {
        this.codecTagString = codecTagString;
    }

    public String getCodecTag() {
        return codecTag;
    }

    public void setCodecTag(String codecTag) {
        this.codecTag = codecTag;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getCodedWidth() {
        return codedWidth;
    }

    public void setCodedWidth(Integer codedWidth) {
        this.codedWidth = codedWidth;
    }

    public Integer getCodedHeight() {
        return codedHeight;
    }

    public void setCodedHeight(Integer codedHeight) {
        this.codedHeight = codedHeight;
    }

    public Integer getHasBFrames() {
        return hasBFrames;
    }

    public void setHasBFrames(Integer hasBFrames) {
        this.hasBFrames = hasBFrames;
    }

    public String getSampleAspectRatio() {
        return sampleAspectRatio;
    }

    public void setSampleAspectRatio(String sampleAspectRatio) {
        this.sampleAspectRatio = sampleAspectRatio;
    }

    public String getDisplayAspectRatio() {
        return displayAspectRatio;
    }

    public void setDisplayAspectRatio(String displayAspectRatio) {
        this.displayAspectRatio = displayAspectRatio;
    }

    public String getPixFmt() {
        return pixFmt;
    }

    public void setPixFmt(String pixFmt) {
        this.pixFmt = pixFmt;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getColorRange() {
        return colorRange;
    }

    public void setColorRange(String colorRange) {
        this.colorRange = colorRange;
    }

    public String getChromaLocation() {
        return chromaLocation;
    }

    public void setChromaLocation(String chromaLocation) {
        this.chromaLocation = chromaLocation;
    }

    public String getFieldOrder() {
        return fieldOrder;
    }

    public void setFieldOrder(String fieldOrder) {
        this.fieldOrder = fieldOrder;
    }

    public Integer getRefs() {
        return refs;
    }

    public void setRefs(Integer refs) {
        this.refs = refs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getrFrameRate() {
        return rFrameRate;
    }

    public void setrFrameRate(String rFrameRate) {
        this.rFrameRate = rFrameRate;
    }

    public String getAvgFrameRate() {
        return avgFrameRate;
    }

    public void setAvgFrameRate(String avgFrameRate) {
        this.avgFrameRate = avgFrameRate;
    }

    public String getTimeBase() {
        return timeBase;
    }

    public void setTimeBase(String timeBase) {
        this.timeBase = timeBase;
    }

    public Integer getStartPts() {
        return startPts;
    }

    public void setStartPts(Integer startPts) {
        this.startPts = startPts;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public Disposition getDisposition() {
        return disposition;
    }

    public void setDisposition(Disposition disposition) {
        this.disposition = disposition;
    }

    public String getSampleFmt() {
        return sampleFmt;
    }

    public void setSampleFmt(String sampleFmt) {
        this.sampleFmt = sampleFmt;
    }

    public String getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(String sampleRate) {
        this.sampleRate = sampleRate;
    }

    public Integer getChannels() {
        return channels;
    }

    public void setChannels(Integer channels) {
        this.channels = channels;
    }

    public String getChannelLayout() {
        return channelLayout;
    }

    public void setChannelLayout(String channelLayout) {
        this.channelLayout = channelLayout;
    }

    public Integer getBitsPerSample() {
        return bitsPerSample;
    }

    public void setBitsPerSample(Integer bitsPerSample) {
        this.bitsPerSample = bitsPerSample;
    }

    public Integer getDurationTs() {
        return durationTs;
    }

    public void setDurationTs(Integer durationTs) {
        this.durationTs = durationTs;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Stream.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("index");
        sb.append('=');
        sb.append(((this.index == null)?"<null>":this.index));
        sb.append(',');
        sb.append("codecName");
        sb.append('=');
        sb.append(((this.codecName == null)?"<null>":this.codecName));
        sb.append(',');
        sb.append("codecLongName");
        sb.append('=');
        sb.append(((this.codecLongName == null)?"<null>":this.codecLongName));
        sb.append(',');
        sb.append("profile");
        sb.append('=');
        sb.append(((this.profile == null)?"<null>":this.profile));
        sb.append(',');
        sb.append("codecType");
        sb.append('=');
        sb.append(((this.codecType == null)?"<null>":this.codecType));
        sb.append(',');
        sb.append("codecTimeBase");
        sb.append('=');
        sb.append(((this.codecTimeBase == null)?"<null>":this.codecTimeBase));
        sb.append(',');
        sb.append("codecTagString");
        sb.append('=');
        sb.append(((this.codecTagString == null)?"<null>":this.codecTagString));
        sb.append(',');
        sb.append("codecTag");
        sb.append('=');
        sb.append(((this.codecTag == null)?"<null>":this.codecTag));
        sb.append(',');
        sb.append("width");
        sb.append('=');
        sb.append(((this.width == null)?"<null>":this.width));
        sb.append(',');
        sb.append("height");
        sb.append('=');
        sb.append(((this.height == null)?"<null>":this.height));
        sb.append(',');
        sb.append("codedWidth");
        sb.append('=');
        sb.append(((this.codedWidth == null)?"<null>":this.codedWidth));
        sb.append(',');
        sb.append("codedHeight");
        sb.append('=');
        sb.append(((this.codedHeight == null)?"<null>":this.codedHeight));
        sb.append(',');
        sb.append("hasBFrames");
        sb.append('=');
        sb.append(((this.hasBFrames == null)?"<null>":this.hasBFrames));
        sb.append(',');
        sb.append("sampleAspectRatio");
        sb.append('=');
        sb.append(((this.sampleAspectRatio == null)?"<null>":this.sampleAspectRatio));
        sb.append(',');
        sb.append("displayAspectRatio");
        sb.append('=');
        sb.append(((this.displayAspectRatio == null)?"<null>":this.displayAspectRatio));
        sb.append(',');
        sb.append("pixFmt");
        sb.append('=');
        sb.append(((this.pixFmt == null)?"<null>":this.pixFmt));
        sb.append(',');
        sb.append("level");
        sb.append('=');
        sb.append(((this.level == null)?"<null>":this.level));
        sb.append(',');
        sb.append("colorRange");
        sb.append('=');
        sb.append(((this.colorRange == null)?"<null>":this.colorRange));
        sb.append(',');
        sb.append("chromaLocation");
        sb.append('=');
        sb.append(((this.chromaLocation == null)?"<null>":this.chromaLocation));
        sb.append(',');
        sb.append("fieldOrder");
        sb.append('=');
        sb.append(((this.fieldOrder == null)?"<null>":this.fieldOrder));
        sb.append(',');
        sb.append("refs");
        sb.append('=');
        sb.append(((this.refs == null)?"<null>":this.refs));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("rFrameRate");
        sb.append('=');
        sb.append(((this.rFrameRate == null)?"<null>":this.rFrameRate));
        sb.append(',');
        sb.append("avgFrameRate");
        sb.append('=');
        sb.append(((this.avgFrameRate == null)?"<null>":this.avgFrameRate));
        sb.append(',');
        sb.append("timeBase");
        sb.append('=');
        sb.append(((this.timeBase == null)?"<null>":this.timeBase));
        sb.append(',');
        sb.append("startPts");
        sb.append('=');
        sb.append(((this.startPts == null)?"<null>":this.startPts));
        sb.append(',');
        sb.append("startTime");
        sb.append('=');
        sb.append(((this.startTime == null)?"<null>":this.startTime));
        sb.append(',');
        sb.append("bitRate");
        sb.append('=');
        sb.append(((this.bitRate == null)?"<null>":this.bitRate));
        sb.append(',');
        sb.append("disposition");
        sb.append('=');
        sb.append(((this.disposition == null)?"<null>":this.disposition));
        sb.append(',');
        sb.append("sampleFmt");
        sb.append('=');
        sb.append(((this.sampleFmt == null)?"<null>":this.sampleFmt));
        sb.append(',');
        sb.append("sampleRate");
        sb.append('=');
        sb.append(((this.sampleRate == null)?"<null>":this.sampleRate));
        sb.append(',');
        sb.append("channels");
        sb.append('=');
        sb.append(((this.channels == null)?"<null>":this.channels));
        sb.append(',');
        sb.append("channelLayout");
        sb.append('=');
        sb.append(((this.channelLayout == null)?"<null>":this.channelLayout));
        sb.append(',');
        sb.append("bitsPerSample");
        sb.append('=');
        sb.append(((this.bitsPerSample == null)?"<null>":this.bitsPerSample));
        sb.append(',');
        sb.append("durationTs");
        sb.append('=');
        sb.append(((this.durationTs == null)?"<null>":this.durationTs));
        sb.append(',');
        sb.append("duration");
        sb.append('=');
        sb.append(((this.duration == null)?"<null>":this.duration));
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
        result = ((result* 31)+((this.hasBFrames == null)? 0 :this.hasBFrames.hashCode()));
        result = ((result* 31)+((this.colorRange == null)? 0 :this.colorRange.hashCode()));
        result = ((result* 31)+((this.fieldOrder == null)? 0 :this.fieldOrder.hashCode()));
        result = ((result* 31)+((this.codecTimeBase == null)? 0 :this.codecTimeBase.hashCode()));
        result = ((result* 31)+((this.codecType == null)? 0 :this.codecType.hashCode()));
        result = ((result* 31)+((this.avgFrameRate == null)? 0 :this.avgFrameRate.hashCode()));
        result = ((result* 31)+((this.duration == null)? 0 :this.duration.hashCode()));
        result = ((result* 31)+((this.startPts == null)? 0 :this.startPts.hashCode()));
        result = ((result* 31)+((this.bitsPerSample == null)? 0 :this.bitsPerSample.hashCode()));
        result = ((result* 31)+((this.codedWidth == null)? 0 :this.codedWidth.hashCode()));
        result = ((result* 31)+((this.durationTs == null)? 0 :this.durationTs.hashCode()));
        result = ((result* 31)+((this.bitRate == null)? 0 :this.bitRate.hashCode()));
        result = ((result* 31)+((this.sampleFmt == null)? 0 :this.sampleFmt.hashCode()));
        result = ((result* 31)+((this.startTime == null)? 0 :this.startTime.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.timeBase == null)? 0 :this.timeBase.hashCode()));
        result = ((result* 31)+((this.height == null)? 0 :this.height.hashCode()));
        result = ((result* 31)+((this.sampleAspectRatio == null)? 0 :this.sampleAspectRatio.hashCode()));
        result = ((result* 31)+((this.channelLayout == null)? 0 :this.channelLayout.hashCode()));
        result = ((result* 31)+((this.level == null)? 0 :this.level.hashCode()));
        result = ((result* 31)+((this.profile == null)? 0 :this.profile.hashCode()));
        result = ((result* 31)+((this.codecTagString == null)? 0 :this.codecTagString.hashCode()));
        result = ((result* 31)+((this.index == null)? 0 :this.index.hashCode()));
        result = ((result* 31)+((this.chromaLocation == null)? 0 :this.chromaLocation.hashCode()));
        result = ((result* 31)+((this.sampleRate == null)? 0 :this.sampleRate.hashCode()));
        result = ((result* 31)+((this.codecTag == null)? 0 :this.codecTag.hashCode()));
        result = ((result* 31)+((this.displayAspectRatio == null)? 0 :this.displayAspectRatio.hashCode()));
        result = ((result* 31)+((this.pixFmt == null)? 0 :this.pixFmt.hashCode()));
        result = ((result* 31)+((this.disposition == null)? 0 :this.disposition.hashCode()));
        result = ((result* 31)+((this.channels == null)? 0 :this.channels.hashCode()));
        result = ((result* 31)+((this.refs == null)? 0 :this.refs.hashCode()));
        result = ((result* 31)+((this.codecLongName == null)? 0 :this.codecLongName.hashCode()));
        result = ((result* 31)+((this.width == null)? 0 :this.width.hashCode()));
        result = ((result* 31)+((this.rFrameRate == null)? 0 :this.rFrameRate.hashCode()));
        result = ((result* 31)+((this.codedHeight == null)? 0 :this.codedHeight.hashCode()));
        result = ((result* 31)+((this.codecName == null)? 0 :this.codecName.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Stream) == false) {
            return false;
        }
        Stream rhs = ((Stream) other);
        return (((((((((((((((((((((((((((((((((((((this.hasBFrames == rhs.hasBFrames)||((this.hasBFrames!= null)&&this.hasBFrames.equals(rhs.hasBFrames)))&&((this.colorRange == rhs.colorRange)||((this.colorRange!= null)&&this.colorRange.equals(rhs.colorRange))))&&((this.fieldOrder == rhs.fieldOrder)||((this.fieldOrder!= null)&&this.fieldOrder.equals(rhs.fieldOrder))))&&((this.codecTimeBase == rhs.codecTimeBase)||((this.codecTimeBase!= null)&&this.codecTimeBase.equals(rhs.codecTimeBase))))&&((this.codecType == rhs.codecType)||((this.codecType!= null)&&this.codecType.equals(rhs.codecType))))&&((this.avgFrameRate == rhs.avgFrameRate)||((this.avgFrameRate!= null)&&this.avgFrameRate.equals(rhs.avgFrameRate))))&&((this.duration == rhs.duration)||((this.duration!= null)&&this.duration.equals(rhs.duration))))&&((this.startPts == rhs.startPts)||((this.startPts!= null)&&this.startPts.equals(rhs.startPts))))&&((this.bitsPerSample == rhs.bitsPerSample)||((this.bitsPerSample!= null)&&this.bitsPerSample.equals(rhs.bitsPerSample))))&&((this.codedWidth == rhs.codedWidth)||((this.codedWidth!= null)&&this.codedWidth.equals(rhs.codedWidth))))&&((this.durationTs == rhs.durationTs)||((this.durationTs!= null)&&this.durationTs.equals(rhs.durationTs))))&&((this.bitRate == rhs.bitRate)||((this.bitRate!= null)&&this.bitRate.equals(rhs.bitRate))))&&((this.sampleFmt == rhs.sampleFmt)||((this.sampleFmt!= null)&&this.sampleFmt.equals(rhs.sampleFmt))))&&((this.startTime == rhs.startTime)||((this.startTime!= null)&&this.startTime.equals(rhs.startTime))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.timeBase == rhs.timeBase)||((this.timeBase!= null)&&this.timeBase.equals(rhs.timeBase))))&&((this.height == rhs.height)||((this.height!= null)&&this.height.equals(rhs.height))))&&((this.sampleAspectRatio == rhs.sampleAspectRatio)||((this.sampleAspectRatio!= null)&&this.sampleAspectRatio.equals(rhs.sampleAspectRatio))))&&((this.channelLayout == rhs.channelLayout)||((this.channelLayout!= null)&&this.channelLayout.equals(rhs.channelLayout))))&&((this.level == rhs.level)||((this.level!= null)&&this.level.equals(rhs.level))))&&((this.profile == rhs.profile)||((this.profile!= null)&&this.profile.equals(rhs.profile))))&&((this.codecTagString == rhs.codecTagString)||((this.codecTagString!= null)&&this.codecTagString.equals(rhs.codecTagString))))&&((this.index == rhs.index)||((this.index!= null)&&this.index.equals(rhs.index))))&&((this.chromaLocation == rhs.chromaLocation)||((this.chromaLocation!= null)&&this.chromaLocation.equals(rhs.chromaLocation))))&&((this.sampleRate == rhs.sampleRate)||((this.sampleRate!= null)&&this.sampleRate.equals(rhs.sampleRate))))&&((this.codecTag == rhs.codecTag)||((this.codecTag!= null)&&this.codecTag.equals(rhs.codecTag))))&&((this.displayAspectRatio == rhs.displayAspectRatio)||((this.displayAspectRatio!= null)&&this.displayAspectRatio.equals(rhs.displayAspectRatio))))&&((this.pixFmt == rhs.pixFmt)||((this.pixFmt!= null)&&this.pixFmt.equals(rhs.pixFmt))))&&((this.disposition == rhs.disposition)||((this.disposition!= null)&&this.disposition.equals(rhs.disposition))))&&((this.channels == rhs.channels)||((this.channels!= null)&&this.channels.equals(rhs.channels))))&&((this.refs == rhs.refs)||((this.refs!= null)&&this.refs.equals(rhs.refs))))&&((this.codecLongName == rhs.codecLongName)||((this.codecLongName!= null)&&this.codecLongName.equals(rhs.codecLongName))))&&((this.width == rhs.width)||((this.width!= null)&&this.width.equals(rhs.width))))&&((this.rFrameRate == rhs.rFrameRate)||((this.rFrameRate!= null)&&this.rFrameRate.equals(rhs.rFrameRate))))&&((this.codedHeight == rhs.codedHeight)||((this.codedHeight!= null)&&this.codedHeight.equals(rhs.codedHeight))))&&((this.codecName == rhs.codecName)||((this.codecName!= null)&&this.codecName.equals(rhs.codecName))));
    }

}
