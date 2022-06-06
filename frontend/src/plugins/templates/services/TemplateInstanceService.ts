import { useEffect, useState } from "react";
import { BACKEND_API, CrudService } from "../imports";
import { CreateTemplateInstance, TemplateInstance } from "../models";

export const TEMPLATE_INSTANCE_SERVICE_ID = 'TemplateInstance';

export const useTemplateInstanceService = (groupId: string, templateId: string) => {
  const [templateInstancesService, setTemplateInstancesService] = useState(new TemplateInstancesService(groupId, templateId));

  useEffect(() => {
    setTemplateInstancesService(new TemplateInstancesService(groupId, templateId));
  }, [groupId, templateId]);

  return [templateInstancesService];
}

export class TemplateInstancesService extends CrudService<TemplateInstance, CreateTemplateInstance> {
  constructor(groupId: string, templateId: string) {
    super(TEMPLATE_INSTANCE_SERVICE_ID, `${BACKEND_API}/groups/${groupId}/templates/${templateId}/instances`);
  }
}