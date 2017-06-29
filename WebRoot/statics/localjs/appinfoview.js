$("#back").on("click",function(){
	window.location.href = "../list";
	
	
});
	
	
<trim prefix="set" suffixOverrides=",">
	<if test="softwareName!=null and softwareName!=''">softwareName=#{softwareName},</if>
	<if test="supportROM!=null and supportROM!=''">supportROM=#{supportROM},</if>
	<if test="interfaceLanguage!=null and interfaceLanguage!=''">interfaceLanguage=#{interfaceLanguage},</if>
	<if test="softwareSize!=null">softwareSize=#{softwareSize},</if>
	<if test="downloads!=null">downloads=#{downloads},</if>
	<if test="flatformId!=null">flatformId=#{flatformId},</if>
	<if test="categoryLevel1!=null">categoryLevel1=#{categoryLevel1},</if>
	<if test="categoryLevel2!=null">categoryLevel2=#{categoryLevel2},</if>
	<if test="categoryLevel3!=null">categoryLevel3=#{categoryLevel3},</if>
	<if test="appInfo!=null and appInfo!=''">appInfo=#{appInfo},</if>
	<if test="versionId!=null">versionId=#{versionId},</if>
	
	<if test="modifyDate!=null">modifyDate=#{modifyDate},</if>
	<if test="modifyBy!=null ">modifyBy=#{modifyBy},</if>
	<if test="creationDate!=null">creationDate=#{creationDate},</if>
	<if test="createdBy!=null">createdBy=#{createdBy},</if>
	<if test="offSaleDate!=null">offSaleDate=#{offSaleDate},</if>
	<if test="onSaleDate!=null ">onSaleDate=#{onSaleDate},</if>
	<if test="status!=null ">status=#{status},</if>
	<if test="devId!=null ">devId=#{devId},</if>
	<if test="updateDate!=null">updateDate=#{updateDate},</if>
	<if test="APKName!=null and APKName!=''">APKName=#{APKName},</if>
	
</trim>	