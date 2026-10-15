package com.example.ultssetup;
/**
 * Very basic SemVer system
 */
public class Version
{
    private final int major;
    private final int minor;
    private final int patch;

    public Version(int major, int minor, int patch)
    {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }
    public Version(int major)
    {
        this.major = major;
        this.minor = 0;
        this.patch = 0;
    }
    public Version(int major, int minor)
    {
        this.major = major;
        this.minor = minor;
        this.patch = 0;
    }
    
    @Override 
    public boolean equals(Object o) {
        if (!(o instanceof Version)) return false;
        if (o == this) return true;
        
        Version other = (Version) o;
        int[] otherArr = other.toArray();
        int[] thisArr = this.toArray();
        for(int i=0; i < thisArr.length; i++) {
            if (otherArr[i] != thisArr[i]) return false;
        }
        
        return true;
    }
    
    public boolean greaterThan(Version other) {
        int[] otherArr = other.toArray();
        int[] thisArr = this.toArray();
        for(int i=0; i < thisArr.length; i++) {
            if (otherArr[i] < thisArr[i]) break;
            if (otherArr[i] > thisArr[i]) return false;
        }
        if (this.equals(other)) return false;
        return true;
    }
    
    public boolean lessThan(Version other) {
        int[] otherArr = other.toArray();
        int[] thisArr = this.toArray();
        for(int i=0; i < thisArr.length; i++) {
            if (otherArr[i] < thisArr[i]) break;
            if (otherArr[i] > thisArr[i]) return true;
        }
        if (this.equals(other)) return false;
        return false;
    }
    
    public int[] toArray() {
        return new int[]{major, minor, patch};
    }
    
    @Override
    public String toString() {
        return "v."+major+"."+minor+"."+patch;
    }
    
    public static Version parse(String str) {
        String newStr = str.replace(" ", "");
        newStr = newStr.replace("v.", "");
        newStr = newStr.replace("v", "");
        newStr = newStr.replace("-", ".");
        newStr = newStr.replace("\n", "");
        //newStr = newStr.replaceAll("\\D+", "");
        int[] versions = new int[3];
        String[] parseArray = newStr.split("\\.");
        for(int i=0; i<parseArray.length && i< versions.length; i++) {
            try {
                versions[i] = Integer.parseInt(parseArray[i].replaceAll("\\D+", ""));
            } catch(NumberFormatException e) {
                continue;
            }
        }
        return new Version(versions[0], versions[1], versions[2]);
    }
}
