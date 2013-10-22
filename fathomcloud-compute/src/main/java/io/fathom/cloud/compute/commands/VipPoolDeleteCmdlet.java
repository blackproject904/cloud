package io.fathom.cloud.compute.commands;

import io.fathom.cloud.commands.TypedCmdlet;
import io.fathom.cloud.compute.services.IpPools;
import io.fathom.cloud.protobuf.CloudModel.VirtualIpPoolData;
import io.fathom.cloud.server.model.Project;

import java.util.List;

import javax.inject.Inject;

import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VipPoolDeleteCmdlet extends TypedCmdlet {
    private static final Logger log = LoggerFactory.getLogger(VipPoolDeleteCmdlet.class);

    @Option(name = "-id", usage = "id", required = true)
    public String id;

    @Option(name = "-cidr", usage = "cidr")
    public List<String> cidr;

    @Inject
    IpPools ipPools;

    public VipPoolDeleteCmdlet() {
        super("vip-pool-delete");
    }

    @Override
    protected VirtualIpPoolData run0() throws Exception {
        Project project = null;
        long poolId = Long.valueOf(id);

        VirtualIpPoolData pool = ipPools.findVirtualIpPool(project, poolId);
        if (pool == null) {
            throw new IllegalArgumentException("Cannot find pool with id: " + poolId);
        }

        ipPools.deleteVirtualIpPool(pool);

        return null;
    }
}
